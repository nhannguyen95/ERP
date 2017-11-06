function [sim] = calculateSim(f, data)

%% Calculate some useful values
[N, M] = size(data);

%% Needs to return this similarity matrix
sim = eye(N);

for i = 1 : N
  for j = 1 : N
    % Calculate the cosine similarity between user i and j
    if (i == j)
      continue;
    elseif (i < j)
      sim(i, j) = f(data(i, :), data(j, :));
    elseif (i > j)
      sim(i, j) = sim(j, i);
    endif
endfor

end
