


INSERT INTO "CUSTOMERS" VALUES
('c58f8dd9-95dc-4d01-836b-4df6b37de527','kein','kein','Taxibetrieb','kein','kein'),
('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e', 'Ringstrasse14 78976 Friedheim','danielaltenburg@email.de','Daniel Altenburg', 'IKK' ,'0876 677854'),
('0f2c88b4-4f8f-4986-b7de-a03445ca8a18', 'Lingustenweg 3a 32984 Oberstadt', 'meyersigi@muster.de', 'Siegfried Meyer', 'AOK', '0876 677854'),
('ec8e18d7-fb99-493d-92d9-e6d31329fd74', 'Dalienweg 23 87654 Jested', 'kromerheinz@muster.de', 'Heinz Kromer', 'Techniker', '08976 989'),
('0ea74725-7bee-4a86-9ab0-89ac3c51a172', 'Im Hirschbühl 34 64532 Aichenberg', 'isanellehoenig@muster.de', 'Isabelle Hönig', 'IKK', '189 675634'),
('54852d85-c1f1-4cd6-bb8d-8631cf9013ee', 'Klosterweg 1 78987 Fahrnau', 'heinerebecca@web.lo', 'Rebecca Heine', 'Privat', '+49 176 4536789');


INSERT INTO "EMPLOYEES" VALUES
('8cf87de5-574d-44ab-bf7f-25dec35cca73','kein','kein','kein','kein','kein', 'kein','kein', '0000','kein','kein','kein'),
('51df6789-1b4f-4e32-b075-ef99e86ad9a5','Carlottenweg 8a 67543 Grünwald','01.07.2015-xxxx','heinzmeier@muster.de','23.07.1967','KV-Eintrag','Heinz Meier','80%','1212', 'SV-Eintrag', '1876 67453', '30'),
('e27a9ccf-a06c-44e1-9191-38983048b0f7', 'Haus am See 2 65476 Graubitten','15.01,2012-xxxx','berndbroedner@muster.de','25.03.1999','KV-Eintrag','Bernd Brödner','450€','1214', 'SV-Eintrag','49 189 3425643','0');

INSERT INTO "NEW_TRIP" VALUES
('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e','51df6789-1b4f-4e32-b075-ef99e86ad9a5','2fb669ec-0659-49fb-af9f-e6f71ce8156f','Lingustenweg 3a 32984 Oberstadt','keine', '','', 'NEIN', '26.02.24, 08:20', 'Freiburg ITZ'),
('0f2c88b4-4f8f-4986-b7de-a03445ca8a18','51df6789-1b4f-4e32-b075-ef99e86ad9a5','e313ccbd-be17-427a-be2f-f3a72f0be5bf','Ringstrasse 14 78976 Friedheim','keine', '','', 'NEIN', '28.01.24, 12:00', 'Freiburg ITZ');

INSERT INTO "TRIPCOLLECTOR" VALUES
('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e','51df6789-1b4f-4e32-b075-ef99e86ad9a5','c58f8dd9-95dc-4d01-836b-4df6b37de527', 'Ringstrasse 14 78976 Friedheim','07764 78564  mit Begleitperson','','KTSs','','2','23.09.23, 23:00','Uni Freiburg' ),
('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e','51df6789-1b4f-4e32-b075-ef99e86ad9a5','66241aed-2891-4da5-8647-a75ed1723709', 'Lingustenweg 3a 32984 Oberstadt','0876 677854','', 'KTSs','','1', '23.09.23, 13:00', 'Uni Freiburg'),
('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e','51df6789-1b4f-4e32-b075-ef99e86ad9a5','95af9d1d-83a6-4a79-bb64-f9fea6b43835', 'Dalienweg 23 87654 Jested', '08976 989 ambulant','','BG Stahl', '','1','23.09.23, 08:00','Uni Freiburg'),
('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e','51df6789-1b4f-4e32-b075-ef99e86ad9a5','c870120c-5f25-4a57-992b-d265cfc4c831', 'Im Hirschbühl 34 64532 Aichenberg ', '189 675634 Bestahlung', '','KTSs','','1', '12.10.23, 12:50','Klinik Singen'),
('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e','51df6789-1b4f-4e32-b075-ef99e86ad9a5','28cc1d7e-07ee-4708-8b88-acb66884c1a1', 'Klosterweg 1 78987 Fahrnau','+49 176 4536789  mit 2x Sitzerhöhung 150€ bar','','bar','','6','12.10.23, 09:00','FLH Zürich');


INSERT INTO "EMPLOYEE_LOG" VALUES
('8cf87de5-574d-44ab-bf7f-25dec35cca73','e213341c-7ac6-4046-b6d6-9dd1291c9610','$2a$10$ZJnnRAjbv0osAHYjl0Xu5uqfSbxhKTzYVXFPcBkQkeHB0Q8xxTlGi','ADMIN','admin'),
('51df6789-1b4f-4e32-b075-ef99e86ad9a5','f32fd3b7-7656-4724-b881-e684fd9d944f','$2a$10$wubxjf.BqCbiN9OLIZ0kIO9G.0JfPkTTmu.f8QAlPoRWxQBG6nlKq','DRIVER','driver');


INSERT INTO "CUSTOMER_LOG" VALUES
('48767ba4-a9a2-4c0b-9ce9-cf0c53e8e45e','78ad4f94-9c94-44de-83ca-611e92fbdd0a','$2a$10$.W2SNWx3xJVQ.XiEgUL/jeRk.TooXPEoYosDUbfU5GS3HWwzAwMbS','CUSTOMER','customer');

INSERT INTO "ADMIN_SETTINGS" VALUES
('af5d1ecd-1c2d-48ef-a3a4-8a0e923d0d75', '10.00','Waldshut-Tiengen','10.00','4.50','2.40');


