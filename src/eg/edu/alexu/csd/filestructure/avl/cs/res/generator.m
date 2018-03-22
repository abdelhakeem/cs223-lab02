random(0);
% n = 10000;
% words = cell(n, 1);
% file = fopen('output.txt', 'w');
% for i = 1 : n
% len = ceil(rand * 15);
% word = '';
% for j = 1 : len
% ch = char('a' + floor(rand() * 26));
% word = strcat(word, ch);
% end
% words{i} = word;
% fprintf(file, '%s\n', word);
% end
% 
% fclose(file);
searchs = randsample(words, 10);
del = randsample(words, 300);
sFile = fopen('search.txt', 'w');
dFile = fopen('del.txt', 'w');
for i = 1 : size(search, 1)
fprintf(sFile, '%s\n', searchs{i});
end
for i = 1 : size(del, 1)
fprintf(dFile, '%s\n', del{i});
end
fclose(sFile);
fclose(dFile);
]