### Input and Output

The **Output** is exactly the same as **AHP**

The **Input** also remains the same, the only difference is that the values of pairwise matrices are now triplets instead of scalars.

```
10
0
Goal
1
Pricing
2
Service Quality
3
Delivery Time
4
Cost based
5
Demand based
6
Reliability
7
Responsiveness
8
Immediate
9
Negotiable
3
0
Provider 1
1
Provider 2
2
Provider 3
4
0 3 1 2 3
1 2 4 5
2 2 6 7
3 2 8 9
4
0 3
1 2 2 3 4  // pairwise(1, 2) = (2, 3, 4), pairwise(2, 1) = (1/4, 1/3, 1/2), pairwise(i, i) = (1, 1, 1)
1 3 1 2 3
3 2 1 2 3
1 1
4 5 1 2 3
2 1
6 7 2 3 4
3 1
8 9 4 5 6
6
4 3
0 1 2 3 4
0 2 6 7 8
1 2 4 5 6
5 3
1 0 1 2 3
2 0 3 4 5
2 1 1 2 3
6 3
1 0 3 4 5
2 0 5 6 7
2 1 4 5 6
7 3
1 0 4 5 6
2 0 2 3 4
1 2 2 3 4
8 3
0 1 0.5 1 2
0 2 2 3 4
1 2 4 5 6
9 3
0 1 1 2 3
0 2 5 6 7
1 2 2 3 4
```

### Java library

[Efficient Java Matrix Library (EJML)](http://ejml.org)

### References

[[1] A Fuzzy AHP Approach for Supplier Selection Problem: A Case Study in a Gear Motor Company](https://arxiv.org/abs/1311.2886)
