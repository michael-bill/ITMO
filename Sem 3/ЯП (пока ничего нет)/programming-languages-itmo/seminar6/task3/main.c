#include <inttypes.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

struct AST
{
  enum AST_type
  {
    AST_BINOP,
    AST_UNOP,
    AST_LIT
  } type;
  union
  {
    struct binop
    {
      enum binop_type
      {
        BIN_PLUS,
        BIN_MINUS,
        BIN_MUL,
        BIN_DIV
      } type;
      struct AST *left, *right;
    } as_binop;
    struct unop
    {
      enum unop_type
      {
        UN_NEG
      } type;
      struct AST *operand;
    } as_unop;
    struct literal
    {
      double value;
    } as_literal;
  };
};

/* DSL */
static struct AST *newnode(struct AST ast)
{
  struct AST *const node = malloc(sizeof(struct AST));
  *node = ast;
  return node;
}

struct AST _lit(double value)
{
  return (struct AST){AST_LIT, .as_literal = {value}};
}

struct AST *lit(double value)
{
  return newnode(_lit(value));
}
struct AST _unop(enum unop_type type, struct AST *operand)
{
  return (struct AST){AST_UNOP, .as_unop = {type, operand}};
}

struct AST *unop(enum unop_type type, struct AST *operand)
{
  return newnode(_unop(type, operand));
}

struct AST _binop(enum binop_type type, struct AST *left, struct AST *right)
{
  return (struct AST){AST_BINOP, .as_binop = {type, left, right}};
}
struct AST *binop(enum binop_type type, struct AST *left, struct AST *right)
{
  return newnode(_binop(type, left, right));
}
#define DECLARE_BINOP(fun, code)                       \
  struct AST *fun(struct AST *left, struct AST *right) \
  {                                                    \
    return binop(BIN_##code, left, right);             \
  }

DECLARE_BINOP(add, PLUS)
DECLARE_BINOP(mul, MUL)
DECLARE_BINOP(sub, MINUS)
DECLARE_BINOP(mydiv, DIV)

#undef DECLARE_BINOP
#define DECLARE_UNOP(fun, code)        \
  struct AST *fun(struct AST *operand) \
  {                                    \
    return unop(UN_##code, operand);   \
  }
DECLARE_UNOP(neg, NEG)
#undef DECLARE_UNOP
/* printer */

static const char *BINOPS[] = {
    [BIN_PLUS] = "+", [BIN_MINUS] = "-", [BIN_MUL] = "*", [BIN_DIV] = "/"};
static const char *UNOPS[] = {[UN_NEG] = "-"};

typedef void(printer)(FILE *, struct AST *);

void print(FILE *f, struct AST *ast);

void print_binop(FILE *f, struct AST *ast)
{
  fprintf(f, "(");
  print(f, ast->as_binop.left);
  fprintf(f, ")");
  fprintf(f, "%s", BINOPS[ast->as_binop.type]);
  fprintf(f, "(");
  print(f, ast->as_binop.right);
  fprintf(f, ")");
}
void print_unop(FILE *f, struct AST *ast)
{
  fprintf(f, "(%s", UNOPS[ast->as_unop.type]);
  print(f, ast->as_unop.operand);
  fprintf(f, ")");
}
void print_lit(FILE *f, struct AST *ast)
{
  fprintf(f, "%lf", ast->as_literal.value);
}

static printer *ast_printers[] = {
    [AST_BINOP] = print_binop, [AST_UNOP] = print_unop, [AST_LIT] = print_lit};

void print(FILE *f, struct AST *ast)
{
  if (ast)
    ast_printers[ast->type](f, ast);
  else
    fprintf(f, "<NULL>");
}

double evaluate(struct AST *ast) {
  if (ast->type == AST_LIT) {
    return ast->as_literal.value;
  } else if (ast->type == AST_BINOP) {
    double left = evaluate(ast->as_binop.left);
    double right = evaluate(ast->as_binop.right);
    switch (ast->as_binop.type) {
      case BIN_PLUS:
        return left + right;
      case BIN_MINUS:
        return left - right;
      case BIN_MUL:
        return left * right;
      case BIN_DIV:
        return left / right;
    }
  } else if (ast->type == AST_UNOP) {
    double operand = evaluate(ast->as_unop.operand);
    switch (ast->as_unop.type) {
      case UN_NEG:
        return -operand;
    }
  }
  return 0;
}

int main() {
  struct AST* ast1 = add(lit(999), lit(728));
  print(stdout, ast1);
  double res = evaluate(ast1);
  printf(" = %lf\n", res);

  struct AST* ast2 = add(lit(4), mul(lit(2), lit(9)));
  print(stdout, ast2);
  res = evaluate(ast2);
  printf(" = %lf\n", res);

  // Выражение (3 + 5) * (9 / 7)
  struct AST* ast3 = mul(add(lit(3), lit(5)), mydiv(lit(9), lit(7)));
  print(stdout, ast3);
  res = evaluate(ast3);
  printf(" = %lf\n", res);

  // Выражение (3 - 5) * (123 / 7)
  struct AST* ast4 = mul(sub(lit(3), lit(5)), mydiv(lit(123), lit(7)));
  print(stdout, ast4);
  res = evaluate(ast4);
  printf(" = %lf\n", res);

  return 0;
}