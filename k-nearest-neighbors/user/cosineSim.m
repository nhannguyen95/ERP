function [sim] = cosineSim(v1, v2)
  sim = v1 * v2' /  (sqrt(v1 * v1') * sqrt(v2 * v2'));
end
