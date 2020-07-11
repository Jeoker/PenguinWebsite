import pickle
import os.path
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request

class drive_handle():

    @staticmethod
    def is_image(fname:str):
        ext = fname.split('.')[-1]
        types = ['JPG','jpg','png','PNG','tif','tiff','TIF','TIFF','jpeg','JPEG','DNG']
        if ext in types:
            return True
        return False

    @staticmethod
    def is_video(fname:str):
        ext = fname.split('.')[-1]
        types = ['MP4','mp4','AVI','avi','MOV']
        if ext in types:
            return True
        return False

    @staticmethod
    def is_label(fname:str):
        ext = fname.split('.')[-1]
        types = ['shp','shx','dbf','prj','cpg','xml']
        if ext in types:
            return True
        return False

    @staticmethod
    def get_type(fname):
        return fname.split('.')[-1].lower()

    @staticmethod
    def get_size(size):
        sz = int(int(size)/1000000)
        return str(sz)

    def get_site(self,folder:str):
        month={'JAN':'01','FEB':'02','MAR':'03','APR':'04','MAY':'05','JUN':'06','JUL':'07','AUG':'08','SEP':'09','OCT':'10','NOV':'11','DEC':'12'}
        info = folder.split('_')
        if len(info) < 2:
            print("error in site name!!")
            print(info)
            return None
        site = info[0]
        date = info[1]
        if not date[1].isdigit():
            date = '0'+date
        t = date[5:9]+'/'+month[date[2:5]]+'/'+date[0:2]
        if not (site,t) in self.sites.keys():
            self.sites[(site,t)] = self.sit_idx
            self.sit_idx += 1
        return self.sites[(site,t)]

    def get_cam(self,cam:str):
        if len(cam) < 1:
            return None
        if not cam in self.cameras.keys():
            self.cameras[cam] = self.cam_idx
            self.cam_idx += 1
        return self.cameras[cam]

    @staticmethod
    def get_image_metadata(raw:dict):
        res = {'w':0,'h':0,'lon':0,'lat':0,'alt':0,'cam':'unknown'}
        if len(raw) == 0:
            return res
        res['w'] = raw['width']
        res['h'] = raw['height']
        if raw.get('location'):
            res['lon'] = raw['location']['longitude']
            res['lat'] = raw['location']['latitude']
            res['alt'] = raw['location']['altitude']
        res['cam'] = raw.get('cameraModel','unknown')
        res['t'] = raw.get('time','0')
        return res

    @staticmethod
    def get_video_metadata(raw:dict):
        res = {'w':None,'h':None,'l':None}
        if not len(raw) == 0:
            res['w'] = raw['width']
            res['h'] = raw['height']
            res['l'] = raw['durationMillis']
        return res

    def __init__(self):

        # If modifying these scopes, delete the file token.pickle.
        SCOPES = ['https://www.googleapis.com/auth/drive.metadata']
        creds = None

        # The file token.pickle stores the user's access and refresh tokens
        if os.path.exists('token.pickle'):
            with open('token.pickle', 'rb') as token:
                creds = pickle.load(token)
        # If there are no (valid) credentials available, let the user log in.
        if not creds or not creds.valid:
            if creds and creds.expired and creds.refresh_token:
                creds.refresh(Request())
            else:
                flow = InstalledAppFlow.from_client_secrets_file(
                    'credentials.json', SCOPES)
                creds = flow.run_local_server(port=0)
            # Save the credentials for the next run
            with open('token.pickle', 'wb') as token:
                pickle.dump(creds, token)
        self.service = build('drive', 'v3', credentials=creds)
        self.img_idx = 1
        self.vid_idx = 1
        self.lab_idx = 1
        self.sit_idx = 1
        self.cam_idx = 1

        fname = "/home/jeoker/Documents/database/project/image.csv"
        f=open(fname,"w+")
        f.close()
        self.fimg_handle = open(fname,"a")
        fname = "/home/jeoker/Documents/database/project/video.csv"
        f=open(fname,"w+")
        f.close()
        self.fvid_handle = open(fname,"a")
        fname = "/home/jeoker/Documents/database/project/label.csv"
        f=open(fname,"w+")
        f.close()
        self.flab_handle = open(fname,"a")
        fname = "/home/jeoker/Documents/database/project/site.csv"
        f=open(fname,"w+")
        f.close()
        self.fsit_handle = open(fname,"a")
        fname = "/home/jeoker/Documents/database/project/camera.csv"
        f=open(fname,"w+")
        f.close()
        self.fcam_handle = open(fname,"a")
        self.sites = {}
        self.cameras = {}

    def read_drive(self):
        # Call the Drive v3 API
        results = self.service.files().list(
            fields="nextPageToken,files(id, name)",
            q="'1cMmHyNQuqDtr2da5pGISYV_Wt330pWkF' in parents"
            # q="'1ZEg-aVSJ66k6YhMAe2rS0aXXBg85ZZWj' in parents and name contains 'DJI_0324'"
        ).execute()

        for folder in results['files']:
            fname = folder['name']
            if 'UAV stitching AgiSoft' in fname:
                continue
            elif fname == "BRYE":
                results0 = self.service.files().list(
                    pageSize=10,
                    q="'"+folder['id']+"' in parents",
                    fields="files(id,name)",
                ).execute()
                for folder0 in results0['files']:
                    self.settle_folder(folder0['name'],folder0['id'])
                continue
            elif "BEAG_" in fname or "BRAS_" in fname or "ORNE_" in fname or "HERO_" in fname or "EARL_" in fname or "EMEL_" in fname:
                results0 = self.service.files().list(
                    pageSize=10,
                    q="'"+folder['id']+"' in parents",
                    fields="files(id,name)",
                ).execute()
                for folder0 in results0['files']:
                    self.settle_folder(folder['name'],folder0['id'])
            self.settle_folder(fname,folder['id'])

    def insert_site(self):
        for site,idx in self.sites.items():
            print('"'+str(idx)+'","'+str(site[0])+'","'+str(site[1])+'"',file=self.fsit_handle)

    def insert_camera(self):
        for cam,idx in self.cameras.items():
            print('"'+str(idx)+'","'+str(cam)+'"',file=self.fcam_handle)

    def insert_image(self,f:dict,site_info:int):
        meta = self.get_image_metadata(f.get('imageMediaMetadata',{}))
        size = self.get_size(f['size'])
        typ = self.get_type(f['name'])
        cam = self.get_cam(meta['cam'])
        print(u'"{0}","{1}","{2}","{3}","{4}","{5}","{6}","{7}","{8}","{9}","{10}","{11}"'.format(
            self.img_idx,f['name'],typ,site_info,f['webViewLink'],size,
            meta['t'],meta['w'],meta['h'],meta['lon'],
            meta['lat'],cam),
            file=self.fimg_handle
        )
        self.img_idx += 1

    def insert_video(self,f:dict,site_info:int):
        meta = self.get_video_metadata(f.get('videoMediaMetadata',{}))
        size = self.get_size(f['size'])
        typ = self.get_type(f['name'])
        print(u'"{0}","{1}","{2}","{3}","{4}","{5}","{6}","{7}","{8}"'.format(
            self.vid_idx,f['name'],typ,site_info,
            meta['w'],meta['h'],meta['l'],size,f['webViewLink']),
            file=self.fvid_handle
        )
        self.vid_idx += 1

    def insert_label(self,f:dict,site_info:int):
        typ = self.get_type(f['name'])
        size = self.get_size(f['size'])
        print(u'"{0}","{1}","{2}","{3}","{4}","{5}"'.format(
            self.lab_idx,f['name'],typ,site_info,
            f['webViewLink'],"withoutChick"),
            file=self.flab_handle
        )
        self.lab_idx += 1

    def settle_folder(self,folder_name,folder_id,token=None):
        if token == None:
            print("working on", folder_name)
        site_info = self.get_site(folder_name)
        results = self.service.files().list(
            orderBy='folder',
            pageSize=100,
            q="'"+folder_id+"' in parents",
            pageToken = token,
            fields="nextPageToken,files(id,name,capabilities(canListChildren),imageMediaMetadata,videoMediaMetadata,size,webViewLink,createdTime)",
        ).execute()

        for f in results['files']:
            if (f['capabilities']['canListChildren']):
                print("  find subfolder, skip:")
                print("    folder name",f['name'])
            elif self.is_image(f['name']):
                self.insert_image(f,site_info)
            elif self.is_video(f['name']):
                self.insert_video(f,site_info)
            elif self.is_label(f['name']):
                self.insert_label(f,site_info)
            else:
                print("  unexpected file name:",f['name'])
            

        new_token = results.get('nextPageToken',None)
        if new_token:
            # print("find token")
            self.settle_folder(folder_name,folder_id,token=new_token)

        return


if __name__ == '__main__':
    dh = drive_handle()
    dh.read_drive()
    dh.insert_site()
    dh.insert_camera()
