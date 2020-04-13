# Matrix Chain Multiplication

This is a dynamic-programming solution to the matrix-chain order problem of wanting to find the minimum number of operations when multiplying *n* matrices together. As we have seen, this is by default an *O*(*n*!) problem, and hence is not solvable with any reasonable input. However, when using a native recursive solution, this turns into *O*(3<sup>*n*</sup>). So, while an improvement over the factorial algorithm, this is still painfully slow on inputs larger than a few hundred or so. Now comes the dynamic-programming solution, reducing our time-complexiy to *O*(*n*<sup>3</sup>) in the worst-case, a *massive* upgrade. 

## Usage

To use this application/algorithm, first clone it to your desktop, and open any IDE of your choice (or just use Notepad and the terminal; I couldn't care less), Then, upon execution, you have to enter your input in a specific way. Take for instance the following input:

5 2 5 4 1 10

This input describes *four* matrices with the following dimensions:

- A<sub>1</sub>: 2 x 5
- A<sub>2</sub>: 5 x 4
- A<sub>3</sub>: 4 x 1
- A<sub>4</sub>: 4 x 10

We use the input as described above to eliminate the possibility of entering a non-multipliable combination of matrices.

