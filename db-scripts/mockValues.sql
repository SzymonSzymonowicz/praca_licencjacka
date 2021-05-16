INSERT INTO `myexaminer`.`account` VALUES
(1,'dianaLektor@gmail.com', '$2y$10$LgWWkVFfYf65dDYueQZVvOY6w.L0tmXArq2RnknDFUNoD8EqZ3AiC', 1, 'Czy jestem lektorem?', 'Tak', 1),
(2,'bruceStudent@gmail.com', '$2y$10$LgWWkVFfYf65dDYueQZVvOY6w.L0tmXArq2RnknDFUNoD8EqZ3AiC', 1, 'Czy jestem uzytkownikiem?', 'Tak', 0),
(3,'clarkStudent@gmail.com', '$2y$10$LgWWkVFfYf65dDYueQZVvOY6w.L0tmXArq2RnknDFUNoD8EqZ3AiC', 1, 'Czy jestem uzytkownikiem?', 'Tak', 0);

INSERT INTO `myexaminer`.`lecturer` VALUES
(1,'Diana', 'Themysciry', 'dianalektor.ug.edu.pl', 'dianalektor@ug.edu.pl');

INSERT INTO `myexaminer`.`student` VALUES
(2,'Bruce', 'Wayne', '250558', 'MFI', 'Informatyka'),
(3,'Clark', 'Kent', '250123', 'MFI', 'Informatyka');

INSERT INTO `myexaminer`.`role` VALUES
(1,'ROLE_STUDENT'),
(2,'ROLE_LECTURER');

INSERT INTO `myexaminer`.`account_role` VALUES
(2,1),
(3,1),
(1,2);

INSERT INTO `myexaminer`.`teaching_group` VALUES
(1, 'Grupa DT2 angielski', 'kod-dostepu-123', NOW(), 1),
(2, 'Grupa Testowa', 'test-123', NOW(), 1),
(3, 'Nowa Grupa', 'qazwsx', NOW(), 1);

INSERT INTO `myexaminer`.`student_teaching_group` VALUES
(1,2),
(1,3),
(2,2);

INSERT INTO `myexaminer`.`exam` VALUES
(1,'English exam - B2', '<b>INSTRUCTIONS TO CANDIDATES</b><br><ul><li>Do not open this exam until you are told to do so.</li>
<li>Answer all the questions.</li><li>You <b>must</b> complete the exam within the time limit.</li></ul>', CURRENT_TIME() + INTERVAL 2 MINUTE,60 ,'OPEN', 1),
(2,'Matura próbna - poziom rozszerzony', 'Próbny arkusz maturalny. Powodzenia! &#128515', '2021-01-20 12:15:00',20 ,'CLOSED', 1);

INSERT INTO `myexaminer`.`exercise` VALUES
(1,'{"type": "O", "instruction": "Zastosuj inwersję: It was only after Sir Fredrick and Charles discovered insulin that diabets patient got the chance to live", "points":2}', 1),
(2,'{"type": "Z", "instruction": "The volume of traffic in ... cities in the world today continues to expand", "answers": ["a few,F", "number,F", "few,F", "many,T"], "points":1}', 1),
(3,'{"type": "O", "instruction": "Za pomocą słowa LIVE przekształć zdanie: John did\'t enjoy the film as much as he had expected.", "points":2}', 1),
(4,'{"type": "Z", "instruction": "The subject of bullying and fighting in my school is:", "answers": ["apple of eye,F", "piece of cake,F", "a bed of roses,F", "a hot potato,T"], "points":1}', 1),
(5,'{"type": "L", "instruction": "Wpisz brakujace słowa", "fill": "One possible <blank> is to make it more expensive for people to use their cars by <blank> charges for parking and introducing tougher fines for anyone who breaks the law.", "points":3}', 1),
(6,'{"type": "O", "instruction": "Zastosuj inwersję: It was only after Sir Fredrick and Charles discovered insulin that diabets patient got the chance to live", "points":2}', 2),
(7,'{"type": "Z", "instruction": "The volume of traffic in ... cities in the world today continues to expand", "answers": ["a few,F", "number,F", "few,F", "many,T"], "points":1}', 2),
(8,'{"type": "O", "instruction": "Za pomocą słowa LIVE przekształć zdanie: John did\'t enjoy the film as much as he had expected.", "points":2}', 2),
(9,'{"type": "Z", "instruction": "The subject of bullying and fighting in my school is:", "answers": ["apple of eye,F", "piece of cake,F", "a bed of roses,F", "a hot potato,T"], "points":1}', 2),
(10,'{"type": "L", "instruction": "Wpisz brakujace słowa", "fill": "One possible <blank> is to make it more expensive for people to use their cars by <blank> charges for parking and introducing tougher fines for anyone who breaks the law.", "points":3}', 2);

-- INSERT INTO `myexaminer`.`individual_exam` VALUES
-- (1, 1, 2, 0),
-- (2, 1, 3, 0),
-- (3, 2, 2, 0),
-- (4, 2, 3, 0);

INSERT INTO `myexaminer`.`notebook` VALUES
(1,'Miłej nauki !',1),
(2,'Miłej nauki !',2),
(3,'Miłej nauki !',3);

INSERT INTO `myexaminer`.`lesson` VALUES
(1, "2021-05-10 10:15:00", "Czasy przeszłe powtórzenie", "past simple, past perfect", 1),
(2, "2021-05-17 10:15:00", "Czasy przyszłe powtórzenie", "future simple, future continuous, future perfect", 1),
(3, "2021-05-24 10:15:00", "Czasy teraźniejsze powtórzenie", "present simple, present perfect", 1);

-- id, title, content, lesson_id
INSERT INTO `myexaminer`.`chapter` VALUES
(1, "Past Simple", "<h2>Past Simple - użycie</h2>Czas Past Simple jest czasem przeszłym, który w dużym stopniu odpowiada polskiemu trybowi dokonanemu. Stosujemy go w następujących sytuacjach:<br><h3>Mówiąc o czynnościach, stanach i sytuacjach, które miały miejsce w przeszłości i nie mają już żadnego związku z teraźniejszością.</h3>W tego typu zdaniach bardzo często pojawiają się określenia czasu precyzujące moment wykonania czynności. Oto kilka przykładów:<ul><li>Jim watched TV yesterday.</li><li>I learnt German many years ago.</li><li>They lived in the countryside when they were younger.</li><li>Mary wrote the report two days ago.</li><li>We visited Paris last month.</li></ul>", 1),
(2, "Past Perfect", "<h2>Past Perfect - użycie</h2>Czasu Past Perfect Simple ma dość ograniczony zakres użycia. Stosujemy go, aby mówić o czynnościach, które zakończyły się przed inną czynnością lub punktem w czasie w przeszłości. <br>Takim punktem w czasie może też być data lub jakieś wydarzenie. Oto kilka przykładów.<br><ul><li>I had packed my suitcases before the taxi arrived.</li><li>Peter had broken down after his wife had left him.</li><li>By the time you came we had discussed all the points on the agenda.</li><li>I wondered who had eaten my piece of cake.</li><li>The company had sold over twenty thousand of these cars by 1999.</li></ul>", 1);
