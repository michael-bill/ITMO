let calculator;
function init() {
    calculator = Desmos.GraphingCalculator(document.getElementById('calculator'), {
        settingsMenu: false,
        expressions: false,
        keypad: false
    });
}
function expression(latex) {
    calculator.setExpression({id: 'graph', latex: latex, color: 'red'});
}