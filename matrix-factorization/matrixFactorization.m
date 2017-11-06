function [nW, nH] = matrixFactorization(R, W, H, K, steps, alpha, lambda)
%% R = W * H' = nW * nH

%% Initializes some useful values
[N, M] = size(R);

%% Calculates and returns the matrices
nW = W;
nH = H';

for step = 1 : steps
  for i = 1 : N
    for j = 1 : M
      if (R(i, j) >= 0)  % Only consider the "observed/rated values"
        error = R(i, j) - nW(i, :) * nH(:, j);

        %% Gradient Descent
        tmpW = nW(i, :);
        tmpH = nH(:, j);

        % Update synchronously
        nW(i, :) = nW(i, :) + alpha * (2 * error * tmpH' - lambda * tmpW);
        nH(:, j) = nH(:, j) + alpha * (2 * error * tmpW' - lambda * tmpH);
      endif
    endfor
  endfor
endfor

nH = nH';

end
