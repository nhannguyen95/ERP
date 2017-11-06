function [p] = predict(data, sim, row, col, K)

[N M] = size(data);

%% Finds people who watch col-th movie
neighborsSim = [];
for i = 1:N
  if ((data(i, col) > 0) && (i != row))
    neighborsSim(end + 1, :) = sim(row, i);
  endif
endfor

%% Collect K nearest neighbors
[neighborsSim idx] = sort(neighborsSim);
neighborsSim = neighborsSim([(end-K+1):end], :);
idx = idx([(end - K + 1):end], :);

% Calculate the predict value in 2 methods
% p = sumPredictFunction(data, col, neighborsSim, idx);

p = devPredictFunction(data, row, col, neighborsSim, idx);
end
