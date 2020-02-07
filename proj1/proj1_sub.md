# Project 1

Caleb Smith (smit3484@purdue.edu)

## Question 1

|Language|Output|
|--------|------|
|C|-1|
|Java|-1|
|Perl|1|
|Python|1|

The results of C and Java are (in a mathematical sense) more "correct" in that they return the actual remainder of the number after division. `-5 / 2 = -2`, `-2 * 2 = -4`, `-5 - -4 = -1`. However, languages like Perl and Python decided that for most use cases having a positive number returned is generally more useful and makes the programmer's life easier.

There are numerous ways to handle this discrepancy. First, scripting languages that do modulo "incorrectly" often have a way of performing the calculation in a "proper" way (for example, by using the `Decimal` class in python). Use of these classes could be required by a coding standard and enforced by a static linter.

TODO: second way to handle.

## Question 2

### Part A

Valgrind Output:

```
==690== 
==690== HEAP SUMMARY:
==690==     in use at exit: 9 bytes in 1 blocks
==690==   total heap usage: 7 allocs, 6 frees, 2,115 bytes allocated
==690== 
==690== 9 bytes in 1 blocks are definitely lost in loss record 1 of 1
==690==    at 0x4837D7B: realloc (vg_replace_malloc.c:826)
==690==    by 0x1092AB: fgets_enhanced (sll_buggy.c:45)
==690==    by 0x109902: main (sll_buggy.c:277)
==690== 
==690== LEAK SUMMARY:
==690==    definitely lost: 9 bytes in 1 blocks
==690==    indirectly lost: 0 bytes in 0 blocks
==690==      possibly lost: 0 bytes in 0 blocks
==690==    still reachable: 0 bytes in 0 blocks
==690==         suppressed: 0 bytes in 0 blocks
==690== 
==690== For counts of detected and suppressed errors, rerun with: -v
==690== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
```

Basically, the `fgets_enhanced()` function is allocating memory that is never freed. It returns a pointer to the heap-allocated string (for the name of the node) which is never freed by the `delete_node()` function.

Fixing this was fairly simple, before the call to `free(temp)` we need to also call `free(temp->str)`. 
