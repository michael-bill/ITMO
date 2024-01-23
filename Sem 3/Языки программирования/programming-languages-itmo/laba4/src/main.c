#include "mem.h"
#include "mem_internals.h"
#include <assert.h>
#include <stdio.h>

#define TEST_SIZE 8192
#define TEST_BLOCK_SIZE 200

void test_heap() {
    struct region *heap = heap_init(TEST_SIZE);
    assert(heap);
    heap_term();
    printf("Test 1 passed\n");
}

void text_expansion() {
    struct region *heap = heap_init(0);
    assert(heap);
    _malloc(TEST_BLOCK_SIZE);
    assert(heap->size == TEST_BLOCK_SIZE);
    printf("Test 2 passed\n");
}

void test_expansion_moved() {
    void *heap = heap_init(0);
    void *lock = heap + TEST_SIZE;
    void *block1 = _malloc(TEST_SIZE);
    void *block2 = _malloc(TEST_SIZE);
    assert(block1);
    assert(block2);
    _free(block1);
    _free(block2);
    _free(lock);
    assert(heap);
    printf("Test 3 passed\n");
}

void test_blocks() {
    heap_init(TEST_SIZE);
    void *block1 = _malloc(TEST_BLOCK_SIZE);
    void *block2 = _malloc(TEST_BLOCK_SIZE);
    void *block3 = _malloc(TEST_BLOCK_SIZE);
    _free(block1);
    assert(block2);
    assert(block3);
    printf("Test 4 passed\n");
    _free(block2);
    assert(block3);
    printf("Test 5 passed\n");
}

int main() {
    test_heap();
    text_expansion();
    test_expansion_moved();
    test_blocks();
    return 0;
}
