% Define the possible range of values
number(1..size).

% Fill the given values
x(X,Y,N) :- given(X,Y,N).

% Check for rows and columns to have unique values

% Slower:
% 1 { x(X,Y,N):number(N) } 1 :- number(X), number(Y).
% :- x(X,Y1,N), x(X,Y2,N), Y1 != Y2.
% :- x(X1,Y,N), x(X2,Y,N), X1 != X2.

1 { x(X,Y,N):number(N) } 1 :- number(X), number(Y).
1 { x(X,Y,N):number(X) } 1 :- number(N), number(Y).
1 { x(X,Y,N):number(Y) } 1 :- number(N), number(X).


% Check for "less than" constraints
:- x(X1,Y1,N1), x(X2,Y2,N2), lessThan(X1,Y1, X2,Y2), N1 >= N2.

#show x/3.