function [sim] = pearsonSim(v1, v2)
  % sim = v1 * v2' /  (sqrt(v1 * v1') * sqrt(v2 * v2'));
  avgV1 = sum(v1) / sum(v1(:) > 0);
  avgV2 = sum(v2) / sum(v2(:) > 0);
  A = []; B = [];
  for i =1:size(v1)(2)
    if (v1(1,i) > 0 && v2(1, i) > 0)
      A(end + 1) = v1(1, i) - avgV1;
      B(end + 1) = v2(1, i) - avgV2;
    endif
  endfor
  sim = (A * B') / sqrt((A * A') * (B * B'));
end
