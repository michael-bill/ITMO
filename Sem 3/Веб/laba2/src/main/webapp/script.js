const graphCanvas = document.getElementById("graph_canvas");
const ctx = graphCanvas.getContext('2d');
const inputX = document.getElementsByName("x_value_check");
const inputY = document.getElementById("y_pos");
const inputR = document.getElementById("radius_select");
const checkButton = document.getElementById("check_button");
const clearButton = document.getElementById("clear_button");
const tbodyResult = document.getElementById("tbody_result");

let selectedValueX;
let selectedValueY;
let selectedValueR;

function submit(x, y, r) {
    if (!validateValues(x, y, r)) {
        alert("Значения введены неверно. Попробуйте еще раз.");
        return;
    }
    fetch('controller' + "?x=" + x + "&y=" + y + "&r=" + r, {method: 'POST'})
  	.then(response => response.json())
  	.then(data => {
	    let jObject = data;
	    tbodyResult.innerHTML = "";
	    points = jObject.values;
	    for (var i = 0; i < jObject.values.length; i++) {
			var o = jObject.values[i];
			tbodyResult.innerHTML =
	        "<tr>" +
	        "<td>" + o.x + "</td>" +
	        "<td>" + o.y + "</td>" +
	        "<td>" + o.r + "</td>" +
	        "<td>" + (o.validation ? "Да" : "Нет") + "</td>" +
	        "<td>" + getTimeFromSeconds(o.currentTime) + "</td>" +
	        "</tr>" + tbodyResult.innerHTML;
		}
		drawGraph();
  	})
  	.catch(error => console.log('Ошибка:', error));
}

function getHistory() {
	fetch('controller' + "?history=true", {method: 'POST'})
  	.then(response => response.json())
  	.then(data => {
	    let jObject = data;
	    tbodyResult.innerHTML = "";
	    points = jObject.values;
	    for (var i = 0; i < jObject.values.length; i++) {
			var o = jObject.values[i];
			tbodyResult.innerHTML =
	        "<tr>" +
	        "<td>" + o.x + "</td>" +
	        "<td>" + o.y + "</td>" +
	        "<td>" + o.r + "</td>" +
	        "<td>" + (o.validation ? "Да" : "Нет") + "</td>" +
	        "<td>" + getTimeFromSeconds(o.currentTime) + "</td>" +
	        "</tr>" + tbodyResult.innerHTML;
		}
		drawGraph();
  	})
  	.catch(error => console.log('Ошибка:', error));
}

function getTimeFromSeconds(seconds) {
    let date = new Date(null);
    date.setSeconds(seconds / 1000);
    let timeString = date.toISOString().substr(11, 8);
    return timeString + " UTC+0";
}

checkButton.onclick = function () {
    submit(selectedValueX, selectedValueY, selectedValueR);
}

clearButton.onclick = function () {
    fetch('controller' + "?clear=true", {method: 'POST'})
  	.then(response => response.json())
  	.then(data => {
		tbodyResult.innerHTML = "";
		points = [];
		drawGraph();
  	})
  	.catch(error => console.log('Ошибка:', error));
}

document.addEventListener("keydown", function(event) {
    if (event.key === "Enter") {
        submit(selectedValueX, selectedValueY, selectedValueR);
    }
});

inputX.forEach(function(checkbox) {
    checkbox.addEventListener('change', function() {
        inputX.forEach(function(checkbox) {
            checkbox.checked = false;
        });
        this.checked = true;
        selectedValueX = Number(this.id.split('_')[2]);
        drawGraph();
    });
});

inputY.onblur = function () {
    let value = inputY.value;
    let pattern = /^[+-]?(?:3(?:\.0+)?|[0-2](?:\.\d+)?)$/;
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

inputR.onchange = function (event) {
    try {
        selectedValueR = Number(event.target.value);
        if (selectedValueR === 0) selectedValueR = undefined;
        drawGraph();
    } catch (ex) { }
}


function validateValues(x, y, r) {
    try {
        x = Number(x);
        y = Number(y);
        r = Number(r);
        if (!(-4 <= x && x <= 4)) return false;
        if (!(-3 <= y && y <= 3)) return false;
        if (!(1 <= r && r <= 5)) return false;
    } catch (ex) {
        return false;
    }
    return true;
}

let one = 45;

graphCanvas.onclick = function (event) {
	let rect = graphCanvas.getBoundingClientRect();
	let x = event.clientX - rect.left;
	let y = event.clientY - rect.top;
    let width = graphCanvas.width;
    let height = graphCanvas.height;
	selectedValueX = (x - (width / 2)) / one;
	selectedValueY = -(y - (height / 2)) / one;
	submit(selectedValueX, selectedValueY, selectedValueR);
	drawGraph();
}

let points = [];

function drawGraph() {
    let width = graphCanvas.width;
    let height = graphCanvas.height;

    ctx.beginPath();
    ctx.clearRect(0, 0, graphCanvas.width, graphCanvas.height);
    ctx.strokeStyle = 'rgb(233,197,245)';
    ctx.fillStyle = 'rgb(233,197,245)';

    // Вертикали
    ctx.moveTo(0, height / 2);
    ctx.lineTo(width, height / 2);
    ctx.moveTo(width / 2, 0);
    ctx.lineTo(width / 2, height);
    ctx.stroke();
    ctx.closePath();

    ctx.strokeStyle = 'rgb(0, 0, 0)';
    ctx.beginPath();
    let i = 0;
    let lSize = 6;
    while (i * one <= width / 2) {
        ctx.moveTo(width / 2 + i * one, height / 2 + lSize / 2);
        ctx.lineTo(width / 2 + i * one, height / 2 - lSize / 2);
        ctx.moveTo(width / 2 + -i * one, height / 2 + lSize / 2);
        ctx.lineTo(width / 2 + -i * one, height / 2 - lSize / 2);
        i++;
    }
    i = 0;
    while (i * one <= height / 2) {
        ctx.moveTo(width / 2 - lSize / 2, height / 2 + i * one);
        ctx.lineTo(width / 2 + lSize / 2, height / 2 + i * one);
        ctx.moveTo(width / 2 - lSize / 2, height / 2 + -i * one);
        ctx.lineTo(width / 2 + lSize / 2, height / 2 + -i * one);
        i++;
    }

    ctx.stroke();
    ctx.closePath();

    // Фигуры
    if (selectedValueR !== undefined) {
        ctx.beginPath();
        let r = selectedValueR * one;
        // Прямоугольник
        ctx.fillRect(width / 2, height / 2, -r / 2, r);

        // Треугольник
        ctx.moveTo(width / 2, height / 2);
        ctx.lineTo(width / 2, height / 2 - r / 2);
        ctx.lineTo(width / 2 - r, height / 2);

        // Круг
        ctx.moveTo(width / 2, height / 2);
        ctx.arc(width / 2, height / 2, r / 2, 0, -3 * Math.PI / 2, false);

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
	        if ((y <= 0 && y >= -r / 2 && x >= 0 && x <= r / 2 && y * y + x * x <= (r / 2) * (r / 2)) ||
			    (y <= 0 && y >= -r && x <= 0 && x >= -r / 2) ||
			    (y >= 0 && x <= 0 && y <= x / 2 + r / 2)) {
				ctx.fillStyle = "rgb(0, 255, 0)";
			} else {
				ctx.fillStyle = "rgb(255, 0, 0)";
			}
			x *= (selectedValueR / point.r);
			y *= (selectedValueR / point.r);
	        ctx.arc((width / 2) + x * one, (height / 2) - y * one, 2, 0, 2 * Math.PI);
	        ctx.fill();
	        ctx.closePath();
	    }
    }
}

getHistory();
drawGraph();