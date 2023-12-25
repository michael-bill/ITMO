function drawClock() {
    const canvas = document.getElementById('clock_canvas');
    const ctx = canvas.getContext('2d');
    const radius = canvas.height / 2 * 0.9;

    // Перемещаем начало координат в центр холста
    ctx.translate(radius, radius);

    drawFace(ctx, radius);
    drawNumbers(ctx, radius);
    drawTime(ctx, radius);
    drawDate(ctx, radius);

    let interval = 1 * 1000;
    setInterval(() => {
        drawFace(ctx, radius);
        drawNumbers(ctx, radius);
        drawTime(ctx, radius);
        drawDate(ctx, radius);
    }, interval);
}

function drawFace(ctx, radius) {
    // Рисуем круглый контур часов
    ctx.beginPath();
    ctx.arc(0, 0, radius * 0.99, 0, 2 * Math.PI);
    ctx.fillStyle = 'white';
    ctx.fill();

    // Рисуем круглую обводку часов
    ctx.lineWidth = 3;
    ctx.strokeStyle = 'black';
    ctx.stroke();

    // Рисуем центр часов
    ctx.beginPath();
    ctx.arc(0, 0, radius * 0.05, 0, 2 * Math.PI);
    ctx.fillStyle = 'black';
    ctx.fill();
}

function drawNumbers(ctx, radius) {
    const ang = (Math.PI / 6);
    ctx.font = radius * 0.15 + "px arial";
    ctx.textBaseline = "middle";
    ctx.textAlign = "center";

    for (let i = 1; i <= 12; i++) {
        const x = (radius * 0.85) * Math.sin(i * ang);
        const y = -(radius * 0.85) * Math.cos(i * ang);
        ctx.fillText(i.toString(), x, y);
    }
}

function drawTime(ctx, radius) {
    const now = new Date();
    let hour = now.getHours();
    let minute = now.getMinutes();
    let second = now.getSeconds();

    // Рисуем часовую стрелку
    hour = hour % 12;
    hour = (hour * Math.PI / 6) +
        (minute * Math.PI / (6 * 60)) +
        (second * Math.PI / (360 * 60));
    drawHand(ctx, hour, radius * 0.5, radius * 0.07);

    // Рисуем минутную стрелку
    minute = (minute * Math.PI / 30) + (second * Math.PI / (30 * 60));
    drawHand(ctx, minute, radius * 0.8, radius * 0.045);

    // Рисуем секундную стрелку
    second = (second * Math.PI / 30);
    drawHand(ctx, second, radius * 0.9, radius * 0.02);
}

function drawHand(ctx, pos, length, width) {
    ctx.beginPath();
    ctx.lineWidth = width;
    ctx.lineCap = "round";
    ctx.moveTo(0, 0);
    ctx.rotate(pos);
    ctx.lineTo(0, -length);
    ctx.stroke();
    ctx.rotate(-pos);
}

function drawDate(ctx, radius) {
    ctx.font = radius * 0.15 + "px arial";
    ctx.textBaseline = "middle";
    ctx.textAlign = "center";
    ctx.fillText(new Date().toDateString(), 0, radius * -0.2);
}

drawClock();