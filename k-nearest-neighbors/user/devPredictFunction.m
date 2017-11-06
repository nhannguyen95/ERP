function [p] = devPredictFunction(data, row, col, sim, idx)
  % Get the rating of K nearest neighbors on col-th movie
  ratingOfNeighbors = [];
  for i = 1 : size(sim)(1)
    ratingOfNeighbors(end + 1, :) = data(idx(i, 1), col);
  endfor

  % Get the average values
  avgR = sum(data(row,:)) / sum(data(row, :)(:) > 0);
  avgN = [];  % Average of neighbors
  for i = 1 : size(idx)(1)
    v = data(idx(i, 1), :);
    avgN(end + 1, :) = sum(v) / sum(v(:) > 0);
  endfor

  p = avgR + (sim' * (ratingOfNeighbors .- avgN)) / sum(abs(sim));
end
