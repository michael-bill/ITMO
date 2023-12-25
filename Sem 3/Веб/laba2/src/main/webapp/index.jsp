<%@ page
language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=0.4">
	<title>Laba2</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>
    <header>
        <p>Билошицкий Михаил Владимирович, Группа P3216, Вариант 23919</p>
    </header>
    <div class="wrapper">
        <canvas id="graph_canvas" width="500" height="500"></canvas>
        <div class="input-area">
            <fieldset id="field_x_value">
                <legend>Выберите координату X:</legend>
				<% for (int i = -4; i <= 4; i++) { %>
					<div>
						<input type="checkbox" id="x_value_<%=i%>" name="x_value_check" />
                        <label for="x_value_<%=i%>"><%=i%></label>
					</div>
				<% } %>
            </fieldset>
            <br>
            <label for="y_pos">Ведите координату Y [-3, 3]:</label>
            <input id="y_pos" name="y_pos" type="text" placeholder="Введите координату Y">
            <br>
            <label for="radius_select">Выберите R:</label>
            <select id="radius_select" name="radius_select">
                <option value="">--Выберите радиус--</option>
                <% for (int i = 1; i <= 5; i++) { %>
                	<option value="<%=i%>"><%=i%></option>
                <% } %>
            </select>
            <br>
            <button id="check_button">Проверить</button>
            <button id="clear_button">Очистить историю</button>
        </div>
    </div>
    <table>
        <tr id="result_head">
	        <th>X</th>
	        <th>Y</th>
	        <th>R</th>
	        <th>Попадание</th>
	        <th>Текущее время</th>
        </tr>
        <tbody id="tbody_result">
			
        </tbody>
    </table>
 	<script src="script.js"></script>
</body>
</html>