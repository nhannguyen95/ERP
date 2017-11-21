data = load('input/data.txt');
fprintf("Data:\n\n");

%% Initializes some parameters
K = 2;

%% Calculate the similarity matrix
% [sim] = calculateSim(@cosineSim, data);
[sim] = calculateSim(@pearsonSim, data);
fprintf("Similarity matrix:\n\n");
sim

%% Now I want to know How would the row-th person
%% rate the col-th movie
K = 2;  % Consider 2 nearest neighbors
row = 4;
col = 1;
p = predict(data, sim, row, col, K);

fprintf("Predict (%d, %d) = %f\n\n", row, col, p);
