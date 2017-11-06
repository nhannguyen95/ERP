function [p] = sumPredictFunction(data, col, sim, idx)
  % Get the rating of K nearest neighbors on col-th movie
  ratingOfNeighbors = [];
  for i = 1 : size(sim)(1)
    ratingOfNeighbors(end + 1, :) = data(idx(i, 1), col);
  endfor

  p = (sim' * ratingOfNeighbors) / sum(abs(sim));
end
