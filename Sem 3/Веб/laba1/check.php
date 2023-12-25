<?php

header("Content-Type: application/json; charset=utf-8");

$method = $_SERVER["REQUEST_METHOD"];
if ($method !== "GET") {
    http_response_code(405);
    echo "{\"message\":\"Method not allowed.\"}";
    exit;
}


$x = $_GET['x'];
$y = $_GET['y'];
$r = $_GET['r'];

if (!is_numeric($x) || !(-4 <= $x && $x <= 4)) {
    http_response_code(400);
    echo "{\"message\":\"X not valid.\"}";
    exit;
}
if (!is_numeric($y) || !(-3 <= $y && $y <= 3)) {
    http_response_code(400);
    echo "{\"message\":\"Y not valid.\"}";
    exit;
}
if (!is_numeric($r) || !(1 <= $r && $r <= 5)) {
    http_response_code(400);
    echo "{\"message\":\"R not valid.\"}";
    exit;
}


$validation = false;
if (($y <= 0 && $y >= -$r / 2 && $x >= 0 && $x <= $r / 2 && $y * $y + $x * $x <= $r * $r) ||
    ($y <= 0 && $y >= -$r && $x <= 0 && $x >= -$r / 2) ||
    ($y >= 0 && $x <= 0 && $y <= $x / 2 + $r / 2)) {
    $validation = true;
}

$current_time = microtime(true);
$response = array(
    'x' => $x,
    'y' => $y,
    'r' => $r,
    'validation' => $validation,
    'elapsedTime' => $current_time - $_SERVER["REQUEST_TIME_FLOAT"],
    'curTime' => $current_time
);

echo json_encode($response);