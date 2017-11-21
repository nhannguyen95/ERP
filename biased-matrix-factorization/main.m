%% Initialization
clear; close all; clc

%% Load the rating data
% Row: user
% Column: item
% Cell(i, j): rating of ith user for jth item
R = load('input/data.txt');  % R(i)(j) = 0 means unrated
R

[N, M] = size(R);

%% Initializing some parameters
K = 3;           % Latent features
lambda = 0;      % Regularization, 0 means no regularization
alpha = 0.0002;  % Learning rate for Gradient Descent
steps = 3000;    % Number of iterations for Gradient Descent

%% We represent R = W * H'
W = rand(N, K);
H = rand(M, K);

%% Calculate the parameters for Biased Matrix Factorization
avgRating = computeAverageRating(R);
usersBias = computeBiasForRow(R, avgRating);
itemsBias = computeBiasForRow(R', avgRating);

%% No regularization
fprintf("Matrix factorization with lambda = 0 (no regularization)\n");
[nW, nH, uBias, iBias, avg] = matrixFactorization(R, W, H, K, usersBias, itemsBias, avgRating, steps, alpha, lambda);
nR = nW * nH' + uBias + iBias' + avg;

fprintf("New data\n");
nR

fprintf("Press enter to continue...\n");
pause;

%% With regularization
lambda = 0.02;
fprintf("Matrix factorization with lambda = 0.02\n");
[nW, nH, uBias, iBias, avg] = matrixFactorization(R, W, H, K, usersBias, itemsBias, avgRating, steps, alpha, lambda);
nR = nW * nH' + uBias + iBias' + avg;

fprintf("New data\n");
nR
