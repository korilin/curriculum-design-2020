from flask import Flask
from PIL import Image, ImageDraw, ImageFont
import os
import shutil
import random
from flask_cors import *

app = Flask(__name__)
CORS(app)

words = ["python", "java", "flask", "spring", "linux", "redis", "mysql", "django", "css", "rust",
         "vue", "element", "axios", "nodejs", "react", "angular", "json", "google", "chrome", "edge",
         "php", "golang", "numpy", "maven", "docker", "android", "https", "github", "markdown", "kotlin",
         "net", "html", "iview", "ant", "chartjs", "hugo", "hexo", "paddle", "jinja", "thymeleaf"]

colors = ["red", "blue", "green", "yellow", "white", "pink", "black"]


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


if __name__ == '__main__':
    app.run()
