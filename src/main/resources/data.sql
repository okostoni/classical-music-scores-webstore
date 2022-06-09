insert into Composer (name, year_of_birth, year_of_death)
values ('Giovanni Pierluigi da Palestrina', 1525, 1594),
       ('Claudio Monteverdi', 1567, 1643),
       ('Antonio Vivaldi', 1678, 1741),
       ('Johann Sebastian Bach', 1685, 1750),
       ('Georg Friedrich Händel', 1685, 1859),
       ('Joseph Haydn', 1732, 1809),
       ('Wolfgang Amadeus Mozart', 1756, 1791),
       ('Ludwig van Beethoven', 1770, 1827),
       ('Franz Schubert', 1797, 1828),
       ('Frédéric Chopin', 1810, 1849),
       ('Ferenc Liszt', 1811, 1886),
       ('Johannes Brahms', 1833, 1897),
       ('Pjotr Iljics Csajkovszkij', 1840, 1893),
       ('Claude Debussy', 1862, 1918),
       ('Igor Stravinszkij', 1882, 1971),
       ('Béla Bartók', 1882, 1945);


insert into Publisher (name)
values ('Bärenreiter Publishers'),
       ('Editio Musica Budapest'),
       ('Boosey and Hawkes'),
       ('G. Henle Publishers'),
       ('MusicNotes'),
       ('Edition Peters'),
       ('Wiener Urtext Edition');




insert into Score (instrument_type, is_available_in_stock, price, title, year_of_creation, composer_id, publisher_id)
values ('CLARINET', true, 4500, 'Clarinet Concerto in A major', 1791, 7, 7),
       ('CLARINET', false, 6000, 'Clarinet Sonata No. 2 in E flat', 1894, 12, 6),
       ('FLUTE', true, 3500, 'Syrinx', 1913, 14, 3),
       ('FLUTE', false, 7000, 'Prélude à l''après-midi d’un faune', 1894, 14, 3),
       ('PIANO', true, 5000, 'Hungarian Rhapsody No.2', 1847, 11, 2),
       ('PIANO', true, 6500, 'Sonata in B minor', 1854, 11, 2),
       ('PIANO', false, 6500, 'Ballade in G minor', 1835, 10, 5),
       ('VIOLIN', true, 5500, 'Violin Concerto in A minor', 1711, 3, 4),
       ('CELLO', false, 4500, 'Cello Suite in G minor', 1717, 4, 1),
       ('ORGAN', true, 7000, 'Triosonatas', 1726, 4, 1),
       ('VOICE', true, 4000, 'Winterreise', 1827, 9, 7),
       ('TRUMPET', true, 3500, 'Trumpet Concerto in E-Flat Major', 1796, 6, 6),
       ('GUITAR', true, 4400, 'Guitar concerto in D major', 1731, 3, 5),
       ('OBOE', false, 5300, 'Sonata in C major for Oboe and Continuo', 1733, 4, 7),
       ('HORN', true, 5900, 'Horn Concerto No. 4 in E flat', 1786, 7, 7);

