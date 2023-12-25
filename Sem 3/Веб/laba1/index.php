<!DOCTYPE html>
<html lang="ru">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=0.4">
    <title>Laba1</title>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Ubuntu+Mono:ital,wght@0,400;0,700;1,400;1,700&display=swap');

        * {
            margin: 0;
            font-family: 'Ubuntu Mono', monospace;
        }

        body {
            height: 100%;
            background-image: url("static/background-img.jpg");
            background-position: center;
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
        }

        :root {
            --header-color: rgba(230, 230, 230, 0.5);
        }

        header {
            background-color: var(--header-color);
            padding: 10px;
            text-align: left;
            font-size: 18px;
        }

        header > p {
            font-family: "Ubuntu Mono", monospace;
        }

        .wrapper {
            display: flex;
        }

        .input-area {
            display: inline-block;
            margin: 10px;
        }

        .input-area legend div {
            display: inline-block;
            margin: 1px;
        }

        #graph_canvas {
            display: inline-block;
            background: white;
            margin: 10px;
            border-radius: 20px;
        }

        table {
            table-layout: fixed;
            width: 100%;
            border-collapse: collapse;
            border: 2px solid rgb(128, 128, 128);
        }

        th, td {
            padding: 1px;
            border: 1px solid rgb(128, 128, 128);
            text-align: center;
        }

        button {
            cursor: pointer;
            background: white;
            border: 1px solid black;
            border-radius: 5px;
            margin: 5px;
            font-size: 16px;
        }

        input[type="text"] {
            border: 1px solid black;
            border-radius: 5px;
            font-size: 16px;
            text-align: center;
            margin: 5px;
        }

        #radius_select {
            border: 1px solid black;
            border-radius: 5px;
            font-size: 16px;
            text-align: center;
            background: white;
        }

        label, legend {
            font-size: 16px;
        }

    </style>
</head>
<body>
    <header>
        <p>Билошицкий Михаил Владимирович, Группа P3216, Вариант 2603</p>
    </header>
    <div class="wrapper">
        <canvas id="graph_canvas" width="500" height="500"></canvas>
        <div class="input-area">
            <fieldset id="field_x_value">
                <legend>Выберите координату X:</legend>
                <?php
                    for ($i = -4; $i <= 4; $i++) {
                        echo
                        "<div>
                        <input type='checkbox' id='x_value_$i' name='x_value_check' />
                        <label for='x_value_$'>$i</label>
                        </div>";
                    }
                ?>
            </fieldset>
            <br>
            <label for="y_pos">Ведите координату Y [-3, 3]:</label>
            <input id="y_pos" name="y_pos" type="text" placeholder="Введите координату Y">
            <br>
            <label for="radius_select">Выберите R:</label>
            <select id="radius_select" name="radius_select">
                <option value="">--Выберите радиус--</option>
                <?php
                    for ($i = 1; $i <= 5; $i++) {
                        echo "<option value='$i'>$i</option>";
                    }
                ?>
            </select>
            <br>
            <button id="check_button">Проверить</button>
            <button id="clear_button">Очистить историю</button>
        </div>
    </div>
    <table>
        <thead id="result_head">
        <td>X</td>
        <td>Y</td>
        <td>R</td>
        <td>Попадание</td>
        <td>Затраченное время (сек)</td>
        <td>Текущее время</td>
        </thead>
        <tbody id="tbody_result">

        </tbody>
    </table>
    <script type="text/javascript" src="script.js"></script>
</body>
</html>