Java Maven VotingSystem
Система голосования, основанная на MySQL. Схема данных: ![schema](https://github.com/SimvolokovP/OOSP-java-VotingSystem/assets/113377637/0278d68f-d910-4436-84c9-22bd98633204)
Подключение к бд происходит через jdbc. В данной системе представляены модели: админ, пользователь, цик, кандитат, которые отвечают за определенные crud-операции.
Для пользователя реализован процесс регистрации и авторизации по логину и паролю: ![users](https://github.com/SimvolokovP/OOSP-java-VotingSystem/assets/113377637/bdf5d719-3971-47d3-8183-bdd8fbaba092)
Модель цик позволяет получать рез-ты голосования в pdf формате с помощью зависимости Apache PDFBox с timestamp названием.
Все приложние является консольным и позволяет попробовать различные операции от разных типов пользователей.
