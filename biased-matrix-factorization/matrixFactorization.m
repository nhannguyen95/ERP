function [nW, nH, uBias, iBias, avg] = matrixFactorization(R, W, H, K, usersBias, itemsBias, avgRating, steps, alpha, lambda)
%% R = W * H' = nW * nH

%% Initializes some useful values
[N, M] = size(R);

%% Calculates and returns the matrices
nW = W;
nH = H';
uBias = usersBias;
iBias = itemsBias;
avg = avgRating

for step = 1 : steps
  for i = 1 : N
    for j = 1 : M
      if (R(i, j) >= 0)  % Only consider the "observed/rated values" (or training data)
        error = R(i, j) - (nW(i, :) * nH(:, j) + avg + uBias(i, 1) + iBias(j, 1));

        %% Gradient Descent
        tmpW = nW(i, :);
        tmpH = nH(:, j);

        % Update synchronously
        avg = avg + alpha * error;
        uBias(i, 1) = uBias(i, 1) + alpha * (error - lambda * uBias(i, 1));
        iBias(j, 1) = iBias(j, 1) + alpha * (error - lambda * iBias(j, 1));
        nW(i, :) = nW(i, :) + alpha * (2 * error * tmpH' - lambda * tmpW);
        nH(:, j) = nH(:, j) + alpha * (2 * error * tmpW' - lambda * tmpH);
      endif
    endfor
  endfor
endfor

nH = nH';

end
