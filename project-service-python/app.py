from flask import Flask, request
from PIL import Image, ImageDraw, ImageFont
import os
import shutil
import random
import xlrd
from flask_cors import *
from pymysql import IntegrityError
from web_mysql import Connector
import re

app = Flask(__name__)
CORS(app)

words = ["python", "java", "flask", "spring", "linux", "redis", "mysql", "django", "css", "rust",
         "vue", "element", "axios", "nodejs", "react", "angular", "json", "google", "chrome", "edge",
         "php", "golang", "numpy", "maven", "docker", "android", "https", "github", "markdown", "kotlin",
         "net", "html", "iview", "ant", "chartjs", "hugo", "hexo", "paddle", "jinja", "thymeleaf"]

colors = ["red", "blue", "green", "yellow", "white", "pink", "black"]

UPLOAD_FOLDER = "static/upload/"
ALLOWED_EXTENSIONS = {'xls', 'xlsx'}

app.config['MAX_CONTENT_LENGTH'] = 20 * 1024 * 1024

connector = Connector()
connector.host = "localhost"
connector.user = "root"
connector.password = "jiebin22"
connector.database = "curriculum_design"


@app.route('/')
def hello_world():
    return '小型软件开发实战（课程设计）--毕业设计选题系统--登陆验证码生成器'


@app.route('/verification/code/<string:time>')
def generator(time):
    num = random.randint(0, 39)
    image = Image.new('RGB', (160, 60), 'skyblue')
    draw = ImageDraw.Draw(image)
    for w in range(160):
        for h in range(60):
            tmp = random.randint(0, 100)
            if tmp < 70:
                color_index = int(tmp / 10)
                draw.point((w, h), fill=colors[color_index])
    font = ImageFont.truetype("static/Cascadia.ttf", 28)
    draw.text((10, 10), words[num], font=font, fill='white')
    if len(os.listdir("static/img")) > 10:
        shutil.rmtree("static/img")
        os.mkdir("static/img")
    image.save("static/img/" + time + ".jpg")
    return words[num]


@app.route('/verification/img/<string:time>')
def get_image(time):
    return app.send_static_file("img/" + time + ".jpg")


def is_allow_file(file_name):
    return '.' in file_name and file_name.split('.')[-1] in ALLOWED_EXTENSIONS


def get_sheet():
    file = request.files['file']
    file_name = file.filename
    if not file or file_name == '' or not is_allow_file(file_name):
        return "error"
    file.save(UPLOAD_FOLDER + file_name)
    book = xlrd.open_workbook(UPLOAD_FOLDER + file_name)
    sheet = book.sheet_by_index(0)
    return sheet


def student_data_verification(sheet, row):
    sno = sheet.cell(row, 0).value
    if len(sno) != 12:
        return ["error", "学号格式错误"]
    name = sheet.cell(row, 1).value
    try:
        grade = int(sheet.cell(row, 2).value)
    except ValueError:
        return ["error", "年份错误"]
    if grade > 2020:
        return ["error", "年份错误"]
    profession = sheet.cell(row, 4).value
    sql = "select * from profession where ProfName=%s;"
    profession_info = connector.fetchone(sql, value=[profession])
    if profession_info is None:
        return ["error", "找不到该专业"]
    try:
        class_number = int(sheet.cell(row, 5).value)
    except ValueError:
        return ["error", "班号错误"]
    return [sno, name, grade, profession_info[0], class_number]


@app.route('/student/upload', methods=['POST'])
def import_student():
    sheet = get_sheet()
    success_num = 0
    error_num = 0
    errors = []
    for row in range(1, sheet.nrows):
        ver_result = student_data_verification(sheet, row)
        if "error" in ver_result:
            error = ver_result[1]
            errors.append("第" + str(row) + "行:" + error)
            error_num += 1
        else:
            sql = "insert into student (SID,SName,Grade,ProfID,ClassNumber) values (%s,%s,%s,%s,%s)"
            try:
                connector.executor(sql, ver_result)
                success_num += 1
            except IntegrityError:
                errors.append("第" + str(row) + "行:" + "已存在该学号")
                error_num += 1
    return {"success_number": success_num, "error_number": error_num, "errors": errors}


def teacher_data_verification(sheet, row):
    tid = sheet.cell(row, 0).value
    name = sheet.cell(row, 1).value
    guide_profession = sheet.cell(row, 2).value
    sql = "select * from profession where ProfName=%s;"
    profession_info = connector.fetchone(sql, value=[guide_profession])
    if profession_info is None:
        return ["error", "找不到该专业"]
    position = sheet.cell(row, 3).value
    if position == "":
        position = "无"
    rank = sheet.cell(row, 4).value
    if rank == "":
        rank = "无"
    phone = sheet.cell(row, 5).value
    if len(phone) != 0:
        if len(phone) != 11:
            return ["error", "手机格式错误"]
        try:
            int(phone)
        except ValueError:
            return ["error", "手机格式错误"]
    else:
        phone = "无"
    email = sheet.cell(row, 6).value
    if len(email) != 0:
        if not re.match(
                r'^[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+){0,4}@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+){0,4}$',
                email):
            return ["error", "邮箱格式错误"]
    else:
        email = "无"
    return [tid, name, position, rank, profession_info[0], phone, email]


@app.route('/teacher/upload', methods=['POST'])
def import_teacher():
    sheet = get_sheet()
    success_num = 0
    error_num = 0
    errors = []
    for row in range(1, sheet.nrows):
        ver_result = teacher_data_verification(sheet, row)
        if "error" in ver_result:
            error = ver_result[1]
            errors.append("第" + str(row) + "行:" + error)
            error_num += 1
        else:
            sql = "insert into teacher (TID,TName,Position,TRank,GuideProfID,Phone,Email) values (%s,%s,%s,%s,%s,%s,%s)"
            try:
                connector.executor(sql, ver_result)
                success_num += 1
            except IntegrityError:
                errors.append("第" + str(row) + "行:" + "已存在该工号")
                error_num += 1
    return {"success_number": success_num, "error_number": error_num, "errors": errors}


if __name__ == '__main__':
    app.run()
