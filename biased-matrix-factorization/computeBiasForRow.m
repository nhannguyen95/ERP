%% `biasVec`: a column vector, biasVec[i] = bias term of row ith of R matrix
function [biasVec] = computeBiasForRow(R, avg)
  rows = size(R)(1, 1);
  biasVec = zeros(rows, 1);

  for row = 1 : rows
    mask = (R(row, :) != -1);
    biasVec(row, 1) = sum((R(row, :)(mask) - avg)) / numel(R(row, :)(mask));
  endfor
end
