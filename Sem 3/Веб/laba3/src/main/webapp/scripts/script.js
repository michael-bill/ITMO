const graphCanvas = document.getElementById("graph_canvas");
const ctx = graphCanvas.getContext('2d');

let points = [];
var selectedValueX;
var selectedValueY;
var selectedValueR = 2;
let one = 70;

// Функция вывода данных на канвас
function drawGraph() {
    let width = graphCanvas.width;
    let height = graphCanvas.height;
    let centerW = width / 2;
    let centerH = height / 2;

    ctx.beginPath();
    ctx.clearRect(0, 0, graphCanvas.width, graphCanvas.height);
    ctx.strokeStyle = 'rgb(233,197,245)';
    ctx.fillStyle = 'rgb(233,197,245)';

    // Вертикали
    ctx.moveTo(0, centerH);
    ctx.lineTo(width, centerH);
    ctx.moveTo(centerW, 0);
    ctx.lineTo(centerW, height);
    ctx.stroke();
    ctx.closePath();

    ctx.strokeStyle = 'rgb(0, 0, 0)';
    ctx.beginPath();
    let i = 0;
    let lSize = 6;
    while (i * one <= centerW) {
        ctx.moveTo(centerW + i * one, centerH + lSize / 2);
        ctx.lineTo(centerW + i * one, centerH - lSize / 2);
        ctx.moveTo(centerW + -i * one, centerH + lSize / 2);
        ctx.lineTo(centerW + -i * one, centerH - lSize / 2);
        i++;
    }
    i = 0;
    while (i * one <= centerH) {
        ctx.moveTo(centerW - lSize / 2, centerH + i * one);
        ctx.lineTo(centerW + lSize / 2, centerH + i * one);
        ctx.moveTo(centerW - lSize / 2, centerH + -i * one);
        ctx.lineTo(centerW + lSize / 2, centerH + -i * one);
        i++;
    }

    ctx.stroke();
    ctx.closePath();

    // Фигуры
    if (selectedValueR !== undefined) {
        ctx.beginPath();
        let r = selectedValueR * one;

        // Квадрат
        ctx.fillRect(centerW, centerH - r, r, r);

        // Треугольник
        ctx.moveTo(centerW, centerH);
        ctx.lineTo(centerW + r / 2, centerH);
        ctx.lineTo(centerW, centerH + r / 2);

        // Круг
        ctx.moveTo(centerW, centerH);
        ctx.arc(centerW, centerH, r / 2, Math.PI, 3 * Math.PI / 2, false);

        ctx.fill();
        ctx.closePath();
    }

    if (points.length > 0) {
        for (let point of points) {
            ctx.beginPath();
            let x = point.x;
            let y = point.y;
            let r = point.r;
            ctx.beginPath();
            if (point.validation) {
                ctx.fillStyle = "rgb(0, 255, 0)";
            } else {
                ctx.fillStyle = "rgb(255, 0, 0)";
            }
            x *= (selectedValueR / r);
            y *= (selectedValueR / r);
            ctx.arc((centerW) + x * one, centerH - y * one, 2, 0, 2 * Math.PI);
            ctx.fill();
            ctx.closePath();
        }
    }
}

// Функция загрузки истории с HistoryServlet
function loadHistory() {
    let tbodyResult = document.getElementById("j_idt2:table_result_data");
    points = [];
    for (let i = 0; i < tbodyResult.rows.length; i++) {
        // Получаем текущую строку
        let row = tbodyResult.rows[i];
        // Получаем данные из каждой ячейки
        let x = Number(row.cells[0].innerText);
        let y = Number(row.cells[1].innerText);
        let r = Number(row.cells[2].innerText);
        let validation = row.cells[3].innerText === "true";
        let currentTime = row.cells[4].innerText;
        points.push({ x: x, y: y, r: r, validation: validation, currentTime: currentTime });
    }
    drawGraph();
}

// Парсинг радиуса из чекбоксов
const inputR = document.querySelectorAll('input[type="checkbox"]');
inputR.forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        inputR.forEach(function(checkbox) {
            checkbox.checked = false;
        });
        this.checked = true;
        selectedValueR = Number(this.id.replace("j_idt2:r_", "").replace("dot", "."));
        drawGraph();
    });
});

inputR.forEach(function(checkbox) {
    if (checkbox.checked) {
        selectedValueR = Number(checkbox.id.replace("j_idt2:r_", "").replace("dot", "."));
        drawGraph();
    }
});

// Парсинг Y из ввода с валидацией
const inputY = document.getElementById("j_idt2:y_pos");
inputY.onblur = function () {
    let value = inputY.value;
    let pattern = /^(-[0-4](?:\.\d+)?|0(\.\d+)?|1(\.\d+)?|2(\.\d+)?)$/;
    if (!value.match(pattern)) {
        if (value.length > 0)
            alert("Значение y ввходится неверно. Попробуйте еще раз.");
        inputY.value = "";
        selectedValueY = undefined;
    } else {
        selectedValueY = Number(value);
        drawGraph();
    }
}

// Парсинг X из выпадающего списка
const inputX = document.getElementById("j_idt2:x_select");
inputX.onchange = function (event) {
    selectedValueX = Number(event.target.value);
    if (selectedValueX === 0) selectedValueX = undefined;
    drawGraph();
}

// Загрузка истории
window.addEventListener('load', function() {
    loadHistory();
    drawGraph();
});

// Проверка значений на валидность
function validateValues(x, y, r) {
    try {
        x = Number(x);
        y = Number(y);
        r = Number(r);
        if (!(-2 <= x && x <= 2)) return false;
        if (!(-5 < y && y < 3)) return false;
        if (!(1 <= r && r <= 3)) return false;
    } catch (ex) {
        return false;
    }
    return true;
}

// Функция отправки данных на сервер
function submit(x, y, r) {
    if (!validateValues(x, y, r)) {
        alert("Значения введены неверно. Попробуйте еще раз.");
        return;
    }
    fetch('validation' + "?x=" + x + "&y=" + y + "&r=" + r, {method: 'GET'})
        .then(response => {
            if (response.ok) {
                location.reload();
            }
        })
        .catch(error => console.log('Ошибка:', error));
}


// Обработка клика по канвасу
graphCanvas.onclick = function (event) {
    let rect = graphCanvas.getBoundingClientRect();
    let x = event.clientX - rect.left;
    let y = event.clientY - rect.top;
    let width = graphCanvas.width;
    let height = graphCanvas.height;
    selectedValueX = (x - (width / 2)) / one;
    selectedValueY = -(y - (height / 2)) / one;

    if (validateValues(selectedValueX, selectedValueY, selectedValueR)) {
        document.getElementById("j_idt2:hiddenValueX").value = selectedValueX;
        document.getElementById("j_idt2:hiddenValueY").value = selectedValueY;
        document.getElementById("j_idt2:check_button").click();
    } else {
        alert("Значения не прошли валидацию. Попробуйте еще раз.");
    }
}