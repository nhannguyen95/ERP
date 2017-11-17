function [avg] = computeAverageRating(R)
  mask = (R != -1);
  avg = sum(R(mask)) / numel(R(mask));
end
