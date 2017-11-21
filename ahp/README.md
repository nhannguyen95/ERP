### Input

Let's take a look at the structure of the input file for [this example](https://en.wikipedia.org/wiki/Analytic_hierarchy_process_%E2%80%93_leader_example):

```
5 // Total number of goal, criteria
0                // Goal id
Choose a Leader  // Goal label
1           // Criteria id
Experience  // Criteria label  
2
Education
3
Charisma
4
Age
3 // Number of alternatives
0    // Alternative id
Tom  // Alternative label
1
Dick
2
Harry
1 // Specify the hierarchy graph - the number of parent nodes (goal and criteria which has sub criteria)
0 4 1 2 3 4  // Goal (id 0) has 4 connections to 4 criteria (id 1, 2, 3, 4)
1    // The number of pairwise matrices comparing the criteria with respect to the goal
0 6  // The pairwise matrix at goal (id 0) is for the comparison between its children (which are 4 criteria), we only need to know 6 values of build the matrix
1 2 4  // pairwise(1(Experience), 2(Education)) = 4, and pairwise (2, 1) = 1/4, pairwise(i, i) = 1
1 3 3
1 4 7
3 2 3
2 4 3
3 4 5
4    // The number of pairwise matrices comparing the alternatives with respect to the criteria
1 3  // The matrix is at Experience (id 1) and we only need to know 3 value to build the matrix
1 0 4  // data(1(Dick), 0(Tom)) = 4, data(0, 1) = 1/4, data(i, i) = 1
0 2 4
1 2 9
2 3
0 1 3
2 0 5
2 1 7
3 3
0 1 5
0 2 9
1 2 4
4 3
1 0 3
0 2 5
1 2 9
```

### Output

The weight (priority) of each alternative, the highest-weight alternative is the best choice:

<img src="https://i.imgur.com/sShvGdL.png"/>


### Java library

[Efficient Java Matrix Library (EJML)](http://ejml.org)

### References

[[1] AHP - Leader Example](https://en.wikipedia.org/wiki/Analytic_hierarchy_process_%E2%80%93_leader_example)
