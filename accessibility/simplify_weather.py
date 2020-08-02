from datetime import datetime,timedelta
from dateutil.relativedelta import relativedelta

f = open("/home/jeoker/apps/weather.csv", "r")
lines = f.read().splitlines()
f.close()
new_lines = []
for line in lines:
    new_line = line.split(',')
    new_line = [new_line[1][1:-1]]+new_line[2:3]+[new_line[5]]+[new_line[-4]]+new_line[-2:]
    if (new_line[-2]=='-59'):
        new_date = datetime.strptime(new_line[0], '%Y-%m-%d %H:%M:%S')
        if new_date.date().year == 2015:
            new_date = new_date + timedelta(days=731)
        elif new_date.date().year == 2018:
            continue
        elif new_date.date().year == 2019:
            new_date = new_date - timedelta(days=1096)
        new_line[0] = str(new_date)
    new_lines += [','.join(new_line[:-2])]

f = open("/home/jeoker/apps/weather_warehouse.csv", "w+")
for line in new_lines:
    f.writelines(line+"\n")
    # print(line)
f.close()
