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

There are numerous ways to handle this discrepancy. Properly unit testing for the results of functions that use modulo operations can help ensure a developer doesn't mistakenly expect the modulo operation to work differently than it does in the language they are using.

Additionally, scripting languages that do modulo "incorrectly" often have a way of performing the calculation in a "proper" way (for example, by using the `Decimal` class in python). Use of these classes could be required by a coding standard and enforced by a static linter.

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

### Part B

Valgrind Output:

```
==32240== Invalid read of size 8
==32240==    at 0x109431: delete_all (sll_fixed.c:106)
==32240==    by 0x109A70: main (sll_fixed.c:327)
==32240==  Address 0x4a62920 is 16 bytes inside a block of size 24 free'd
==32240==    at 0x48369AB: free (vg_replace_malloc.c:530)
==32240==    by 0x109453: delete_all (sll_fixed.c:109)
==32240==    by 0x109A2C: main (sll_fixed.c:314)
==32240==  Block was alloc'd at
==32240==    at 0x483577F: malloc (vg_replace_malloc.c:299)
==32240==    by 0x10971F: append (sll_fixed.c:209)
==32240==    by 0x10992F: main (sll_fixed.c:281)
==32240==
==32240== Invalid read of size 8
==32240==    at 0x10943D: delete_all (sll_fixed.c:108)
==32240==    by 0x109A70: main (sll_fixed.c:327)
==32240==  Address 0x4a62910 is 0 bytes inside a block of size 24 free'd
==32240==    at 0x48369AB: free (vg_replace_malloc.c:530)
==32240==    by 0x109453: delete_all (sll_fixed.c:109)
==32240==    by 0x109A2C: main (sll_fixed.c:314)
==32240==  Block was alloc'd at
==32240==    at 0x483577F: malloc (vg_replace_malloc.c:299)
==32240==    by 0x10971F: append (sll_fixed.c:209)
==32240==    by 0x10992F: main (sll_fixed.c:281)
==32240==
==32240== Invalid free() / delete / delete[] / realloc()
==32240==    at 0x48369AB: free (vg_replace_malloc.c:530)
==32240==    by 0x109447: delete_all (sll_fixed.c:108)
==32240==    by 0x109A70: main (sll_fixed.c:327)
==32240==  Address 0x4a628c0 is 0 bytes inside a block of size 5 free'd
==32240==    at 0x48369AB: free (vg_replace_malloc.c:530)
==32240==    by 0x109447: delete_all (sll_fixed.c:108)
==32240==    by 0x109A2C: main (sll_fixed.c:314)
==32240==  Block was alloc'd at
==32240==    at 0x483577F: malloc (vg_replace_malloc.c:299)
==32240==    by 0x10921D: fgets_enhanced (sll_fixed.c:29)
==32240==    by 0x10991D: main (sll_fixed.c:281)
==32240==
==32240== Invalid free() / delete / delete[] / realloc()
==32240==    at 0x48369AB: free (vg_replace_malloc.c:530)
==32240==    by 0x109453: delete_all (sll_fixed.c:109)
==32240==    by 0x109A70: main (sll_fixed.c:327)
==32240==  Address 0x4a62910 is 0 bytes inside a block of size 24 free'd
==32240==    at 0x48369AB: free (vg_replace_malloc.c:530)
==32240==    by 0x109453: delete_all (sll_fixed.c:109)
==32240==    by 0x109A2C: main (sll_fixed.c:314)
==32240==  Block was alloc'd at
==32240==    at 0x483577F: malloc (vg_replace_malloc.c:299)
==32240==    by 0x10971F: append (sll_fixed.c:209)
==32240==    by 0x10992F: main (sll_fixed.c:281)
==32240==
```

Note that we get the same error result regardless of running the edit operation or not, so that doesn't appear to be the issue.

When the exit command is run, exit calls `delete_all()` again. However, `delete_all()` fails to set the global head pointer `p` to NULL, so it results in the linked list being double freed. This is fixed by simply setting `p = NULL` at the end of the `delete_all()` function.

### Part C

Test Case:

```
[(i)nsert,(d)elete,delete (a)ll,d(u)plicate,(e)dit,(p)rint,e(x)it]:i
enter the tel:>100
enter the name:>[Ctrl+D]
```

Note: on \*nix the `[Ctrl+D]` key combination sends an End of Transmission (EOT) character, ASCII value (0x04) - a different key combination may be necessary on different operating systems.

Valgrind Output:

```
fgets returned NULL
==29155== Use of uninitialised value of size 8
==29155==    at 0x109354: fgets_enhanced (sll_fixed.c:65)
==29155==    by 0x109928: main (sll_fixed.c:284)
==29155==
==29155== Invalid write of size 1
==29155==    at 0x109354: fgets_enhanced (sll_fixed.c:65)
==29155==    by 0x109928: main (sll_fixed.c:284)
==29155==  Address 0xf0b0ff is not stack'd, malloc'd or (recently) free'd
==29155==
==29155==
==29155== Process terminating with default action of signal 11 (SIGSEGV)
==29155==  Access not within mapped region at address 0xF0B0FF
==29155==    at 0x109354: fgets_enhanced (sll_fixed.c:65)
==29155==    by 0x109928: main (sll_fixed.c:284)
==29155==  If you believe this happened as a result of a stack
==29155==  overflow in your program's main thread (unlikely but
==29155==  possible), you can try to increase the size of the
==29155==  main thread stack using the --main-stacksize= flag.
==29155==  The main thread stack size used in this run was 8388608.
==29155==
==29155== HEAP SUMMARY:
==29155==     in use at exit: 5 bytes in 1 blocks
==29155==   total heap usage: 3 allocs, 2 frees, 2,053 bytes allocated
==29155==
==29155== LEAK SUMMARY:
==29155==    definitely lost: 0 bytes in 0 blocks
==29155==    indirectly lost: 0 bytes in 0 blocks
==29155==      possibly lost: 0 bytes in 0 blocks
==29155==    still reachable: 5 bytes in 1 blocks
==29155==         suppressed: 0 bytes in 0 blocks
==29155== Reachable blocks (those to which a pointer was found) are not shown.
==29155== To see them, rerun with: --leak-check=full --show-leak-kinds=all
==29155==
==29155== For counts of detected and suppressed errors, rerun with: -v
==29155== Use --track-origins=yes to see where uninitialised values come from
==29155== ERROR SUMMARY: 2 errors from 2 contexts (suppressed: 0 from 0)
Segmentation fault
```

By pressing `[Ctrl+D]` before typing any input, we force `fgets()` to return `NULL` upon its first call inside `fgets_enhanced()`. Thus, it skips the majority of the function (contained within the `else` block after checking whether `fgets()` returned `NULL`). Execution reaches the line `s[nPos-s] = 0;` however at this point `nPos` has not been initialized.

To fix this, there are two changes we make. First, we explicitly initialize `nPos` to `NULL` instead of waiting for the first call to `strchr()` to set it to `NULL` (which we just demonstrated doesn't always happen). Then, we add logic to verify that `nPos` is *not* `NULL` before null-terminating `s`.

Of course, this fix revealed another bug. At this point, the program will forever loop, printing the command prompt message over and over again. This is because the `scanf("%s",&c);` in main never has its return value checked. As the input stream has been closed from `[Ctrl+D]`, `scanf()` will not block and will instead immediately return `EOF`.

To fix this issue, we simply add a check on the return value of `scanf()` and terminate the program if the input was `EOF`.

## Question 3

### Part A

![CFG diagram](diagram-3a.png)

### Part B

Note: nodes are numbered from top to bottom and then left to right.

Test Requirement Sets:

```
NC: { n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, n10}

EC: { [0,1], [0,2], [1,2], [2,3], [2,4], [2,5],
      [2,6], [5,6], [3,7], [4,7], [6,7], [7,8], 
      [7,9], [8,10], [9,10] }

EPC: { [0,1,2], [0,2,3], [0,2,4], [0,2,5], [0,2,6],
       [1,2,3], [1,2,4], [1,2,5], [1,2,6], [2,3,7],
       [2,4,7], [2,5,6], [2,6,7], [3,7,9], [4,7,8],
       [5,6,7], [6,7,8], [7,8,10], [7,9,10] }

Infeasible EPC:
[3,7,8] requires i == 0 AND i > 0
[4,7,9] requires i == 1 AND i <= 0
[6,7,9] requires i >= 2 AND i <= 0

PPC: { [0,1,2,3,7,9,10],
       [0,1,2,4,7,8,10],
       [0,1,2,5,6,7,8,10],
       [0,1,2,6,7,8,10]
       [0,2,3,7,9,10],
       [0,2,4,7,8,10],
       [0,2,5,6,7,8,10],
       [0,2,6,7,8,10] }

Infeasible PPC:
[0,1,2,3,7,8,10] as it contains [3,7,8] (see above)
[0,1,2,4,7,9,10] as it contains [4,7,9]
[0,1,2,5,6,7,9,10] as it contains [6,7,9]
[0,1,2,6,7,9,10] as it contains [6,7,9]
[0,2,3,7,8,10] as it contains [3,7,8]
[0,2,4,7,9,10] as it contains [4,7,9]
[0,2,5,6,7,9,10] as it contains [6,7,9]
[0,2,6,7,9,10] as it contains [6,7,9]
```

## Question 4

### addNode()

The test class does satisfy both NC and EC for the `addNode()` method. The test method `addNode()` covers all nodes, and the `addNode_duplicate()` method covers the edge which skips the body of the if statement.

### addEdge()

With the way I've written my implementation of `addEdge()` there are no conditional branches, so the test method `addEdge()` satisfies both NC and EC.

### deleteNode()

The test class satisfies both NC and EC. There are two branches of execution, one where the node exists and one where the node does not exist. The two test methods, `deleteNode()` and `deleteNode_missing()` together satisfy both NC and EC.

### deleteEdge()

The test class satisfies both NC and EC. Again, there are only two branches of execution, one where the map of edges contains a set entry for the first node and one where the map does not. The test methods `deleteEdge()` and `deleteEdge_missing()` together satisfy both NC and EC by covering both of these paths of execution.

### isReachable()

The test class satisfies both NC and EC. The test method `reachable_true()` executes the edges for all of the "true" outcomes on the two if statements and will have to iterate inside the for loop at least once (since there has to be nodes following the first node if the two nodes are connected). It also covers both branches of the first if statement, as on the first iteration the two are not equal but on some later iteration they are. The other three test methods cover the "return false" node. The test method `reachable_missingSrc()` covers the edge for a `false` result on the second if statement, as the node with a negative source code line will not exist in the map of edges. The test method `reachable_unreachable()` covers the edge where the node exists in the map of edges but has no nodes after it, so the second if statement results in a `true` result but the body of the for loop is never executed (this edge is executed once the end of the CFG is reached but the specified end node is never found).
