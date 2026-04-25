package ru.school.aliassociety;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends Activity {
    private static final int EASY = 1;
    private static final int MEDIUM = 2;
    private static final int HARD = 3;
    private static final int ROUND_SECONDS = 60;
    private static final int TARGET_SCORE = 20;

    private final Random random = new Random();
    private final ArrayList<Card> cards = new ArrayList<>();
    private final ArrayList<Card> roundDeck = new ArrayList<>();
    private final ArrayList<String> teamNames = new ArrayList<>();
    private final ArrayList<EditText> teamInputs = new ArrayList<>();
    private int[] scores = new int[]{0, 0};
    private int teamCount = 2;
    private int currentTeam = 0;
    private int currentDifficulty = MEDIUM;
    private int cardIndex = 0;
    private int timeLeft = ROUND_SECONDS;
    private CountDownTimer timer;
    private TextView timerView;
    private TextView scoreView;
    private TextView teamView;
    private TextView cardTermView;
    private TextView cardHintView;
    private SoundPool soundPool;
    private int soundClick;
    private int soundCorrect;
    private int soundSkip;
    private int soundTimerEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(Color.rgb(74, 20, 140));
        window.setNavigationBarColor(Color.rgb(74, 20, 140));
        initSounds();
        initTeams();
        initCards();
        showMainMenu();
    }

    @Override
    protected void onDestroy() {
        stopTimer();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        stopTimer();
        showMainMenu();
    }

    private void initSounds() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(4).setAudioAttributes(attributes).build();
        soundClick = soundPool.load(this, R.raw.click, 1);
        soundCorrect = soundPool.load(this, R.raw.correct, 1);
        soundSkip = soundPool.load(this, R.raw.skip, 1);
        soundTimerEnd = soundPool.load(this, R.raw.timer_end, 1);
    }

    private void play(int id) {
        if (soundPool != null && id != 0) soundPool.play(id, 0.9f, 0.9f, 1, 0, 1f);
    }

    private void initTeams() {
        teamNames.clear();
        teamNames.add("Команда 1");
        teamNames.add("Команда 2");
        scores = new int[]{0, 0};
    }

    private void initCards() {
        cards.clear();
        add(EASY, "общество", "люди, связи, нормы и общая жизнь");
        add(EASY, "индивид", "отдельный человек как представитель вида");
        add(EASY, "личность", "человек с социальными качествами");
        add(EASY, "социализация", "усвоение норм и образцов поведения");
        add(EASY, "семья", "малая группа, брак, родство, быт");
        add(EASY, "школа", "социальный институт образования");
        add(EASY, "государство", "публичная власть, территория, законы");
        add(EASY, "закон", "официальное правило, обязательное для всех");
        add(EASY, "право", "система общеобязательных норм");
        add(EASY, "мораль", "представления о добре, долге и совести");
        add(EASY, "экономика", "производство, обмен, распределение, потребление");
        add(EASY, "потребности", "то, что необходимо человеку или обществу");
        add(EASY, "ресурсы", "средства для производства и жизни");
        add(EASY, "труд", "деятельность по созданию полезного результата");
        add(EASY, "собственность", "принадлежность имущества человеку или организации");
        add(EASY, "деньги", "универсальный посредник обмена");
        add(EASY, "рынок", "сфера обмена товаров и услуг");
        add(EASY, "товар", "продукт, созданный для продажи");
        add(EASY, "услуга", "полезное действие, а не вещь");
        add(EASY, "спрос", "желание и возможность купить");
        add(EASY, "предложение", "готовность продавца продать товар");
        add(EASY, "цена", "денежное выражение стоимости");
        add(EASY, "налоги", "обязательные платежи государству");
        add(EASY, "гражданин", "человек с устойчивой правовой связью с государством");
        add(EASY, "выборы", "голосование за кандидатов или партии");
        add(EASY, "конституция", "основной закон государства");
        add(EASY, "власть", "способность проводить свою волю");
        add(EASY, "культура", "ценности, знания, традиции, искусство");
        add(EASY, "наука", "система знаний и способ их получения");
        add(EASY, "образование", "обучение, воспитание, развитие человека");
        add(EASY, "религия", "вера в сверхъестественное и культовая практика");
        add(EASY, "искусство", "образное освоение мира");
        add(EASY, "социальная группа", "люди с общим признаком или интересом");
        add(EASY, "социальный статус", "положение человека в обществе");
        add(EASY, "социальная роль", "ожидаемое поведение по статусу");
        add(EASY, "конфликт", "столкновение интересов или ценностей");
        add(EASY, "природа", "естественная среда существования человека");
        add(EASY, "прогресс", "развитие от менее совершенного к более сложному");
        add(EASY, "личный бюджет", "доходы и расходы человека или семьи");
        add(EASY, "потребитель", "тот, кто покупает и использует товар");
        add(EASY, "производитель", "тот, кто создает товар или услугу");
        add(EASY, "трудовая деятельность", "работа, направленная на результат");
        add(EASY, "общение", "обмен информацией и эмоциями между людьми");
        add(EASY, "познание", "получение знаний о мире");
        add(EASY, "истина", "знание, соответствующее действительности");

        add(MEDIUM, "правоспособность", "способность иметь права и обязанности");
        add(MEDIUM, "дееспособность", "способность своими действиями приобретать права");
        add(MEDIUM, "юридическая ответственность", "неблагоприятные последствия за правонарушение");
        add(MEDIUM, "правонарушение", "виновное противоправное деяние");
        add(MEDIUM, "преступление", "общественно опасное деяние по уголовному закону");
        add(MEDIUM, "проступок", "менее опасное правонарушение");
        add(MEDIUM, "демократия", "народовластие и политическое участие граждан");
        add(MEDIUM, "федерация", "государство из субъектов с самостоятельностью");
        add(MEDIUM, "республика", "форма правления с выборными органами власти");
        add(MEDIUM, "гражданское общество", "самоорганизация граждан вне прямого управления государства");
        add(MEDIUM, "правовое государство", "власть ограничена правом, права человека защищены");
        add(MEDIUM, "политическая партия", "организация для участия в борьбе за власть");
        add(MEDIUM, "референдум", "прямое голосование граждан по важному вопросу");
        add(MEDIUM, "разделение властей", "законодательная, исполнительная и судебная ветви");
        add(MEDIUM, "местное самоуправление", "решение вопросов территории населением");
        add(MEDIUM, "рынок труда", "сфера спроса и предложения рабочей силы");
        add(MEDIUM, "безработица", "отсутствие работы у тех, кто ищет трудоустройство");
        add(MEDIUM, "инфляция", "устойчивый рост общего уровня цен");
        add(MEDIUM, "ВВП", "стоимость конечных товаров и услуг страны за год");
        add(MEDIUM, "бюджет", "план доходов и расходов");
        add(MEDIUM, "предпринимательство", "инициативная деятельность ради прибыли");
        add(MEDIUM, "конкуренция", "соперничество участников рынка");
        add(MEDIUM, "монополия", "господство одного продавца или производителя");
        add(MEDIUM, "банк", "финансовая организация для денег, кредитов и вкладов");
        add(MEDIUM, "кредит", "деньги в долг с возвратом и процентом");
        add(MEDIUM, "депозит", "банковский вклад");
        add(MEDIUM, "ценная бумага", "документ или запись, подтверждающая имущественное право");
        add(MEDIUM, "акция", "доля в компании и право на дивиденды");
        add(MEDIUM, "облигация", "долговая ценная бумага");
        add(MEDIUM, "издержки", "затраты производителя");
        add(MEDIUM, "прибыль", "доход минус расходы");
        add(MEDIUM, "мировоззрение", "система взглядов человека на мир");
        add(MEDIUM, "духовная культура", "идеи, ценности, знания, вера, искусство");
        add(MEDIUM, "социальная стратификация", "расслоение общества на группы");
        add(MEDIUM, "социальная мобильность", "изменение положения человека или группы");
        add(MEDIUM, "нация", "историческая общность людей с культурой и самосознанием");
        add(MEDIUM, "межнациональные отношения", "связи между этносами и народами");
        add(MEDIUM, "субкультура", "культура отдельной социальной группы");
        add(MEDIUM, "массовая культура", "культура, рассчитанная на широкую аудиторию");
        add(MEDIUM, "элитарная культура", "культура для подготовленной аудитории");
        add(MEDIUM, "народная культура", "традиции, фольклор, обычаи");
        add(MEDIUM, "социальный лифт", "механизм повышения статуса");
        add(MEDIUM, "гражданские права", "права, связанные со свободой и личной защитой");
        add(MEDIUM, "политические права", "права на участие в управлении государством");
        add(MEDIUM, "экономические права", "права в сфере труда, собственности и предпринимательства");

        add(HARD, "гражданство РФ", "устойчивая правовая связь лица с Российской Федерацией");
        add(HARD, "конституционный строй", "основы организации государства и общества");
        add(HARD, "суверенитет", "верховенство и независимость государственной власти");
        add(HARD, "публичная власть", "власть государства и местного самоуправления");
        add(HARD, "альтернативная гражданская служба", "замена военной службы по убеждениям или вероисповеданию");
        add(HARD, "административная ответственность", "санкции за административные правонарушения");
        add(HARD, "презумпция невиновности", "обвиняемый считается невиновным, пока вина не доказана");
        add(HARD, "трудовой договор", "соглашение работника и работодателя");
        add(HARD, "коллективный договор", "акт регулирования социально-трудовых отношений в организации");
        add(HARD, "социальное государство", "государство, поддерживающее достойную жизнь и помощь нуждающимся");
        add(HARD, "государственный бюджет", "централизованный финансовый план государства");
        add(HARD, "дефицит бюджета", "расходы бюджета выше доходов");
        add(HARD, "профицит бюджета", "доходы бюджета выше расходов");
        add(HARD, "денежно-кредитная политика", "регулирование денег, ставок и кредитования");
        add(HARD, "Центральный банк", "главный регулятор денежной системы");
        add(HARD, "фискальная политика", "регулирование экономики через налоги и расходы бюджета");
        add(HARD, "внешние эффекты", "побочные последствия сделки для третьих лиц");
        add(HARD, "общественные блага", "блага, которыми пользуются многие и трудно исключить неплательщика");
        add(HARD, "рациональное экономическое поведение", "выбор с учетом выгод, затрат и ограничений");
        add(HARD, "эластичность спроса", "чувствительность спроса к изменению цены");
        add(HARD, "разделение труда", "распределение операций между участниками производства");
        add(HARD, "специализация", "сосредоточение на отдельном виде деятельности");
        add(HARD, "глобализация", "усиление взаимосвязи стран, рынков и культур");
        add(HARD, "информационное общество", "общество, где информация становится главным ресурсом");
        add(HARD, "постиндустриальное общество", "общество услуг, знаний и технологий");
        add(HARD, "гражданская культура", "ответственное участие граждан в политической жизни");
        add(HARD, "политический режим", "способы осуществления власти");
        add(HARD, "тоталитаризм", "полный контроль государства над обществом");
        add(HARD, "авторитаризм", "сильная власть при ограниченном политическом участии");
        add(HARD, "либерализм", "идеология свободы личности и прав человека");
        add(HARD, "консерватизм", "идеология традиции, порядка и преемственности");
        add(HARD, "социал-демократия", "идеология социальной защиты и демократических процедур");
        add(HARD, "избирательная система", "порядок проведения выборов и распределения мандатов");
        add(HARD, "мажоритарная система", "побеждает кандидат с большинством голосов");
        add(HARD, "пропорциональная система", "мандаты распределяются по доле голосов партий");
        add(HARD, "смешанная система", "сочетание мажоритарного и пропорционального принципов");
        add(HARD, "социальный институт", "устойчивая форма организации общественной жизни");
        add(HARD, "социальные нормы", "правила поведения, принятые в обществе");
        add(HARD, "девиантное поведение", "отклонение от социальных норм");
        add(HARD, "социальный контроль", "механизмы поддержания норм");
        add(HARD, "агенты социализации", "люди и институты, влияющие на развитие личности");
        add(HARD, "этнос", "общность с происхождением, культурой и самосознанием");
        add(HARD, "ксенофобия", "неприязнь к чужим группам и культурам");
        add(HARD, "толерантность", "уважение к различиям и готовность к мирному взаимодействию");
    }

    private void add(int difficulty, String term, String hint) {
        cards.add(new Card(difficulty, term, hint));
    }

    private void showMainMenu() {
        stopTimer();
        LinearLayout root = baseLayout(Color.rgb(123, 31, 162), Color.rgb(0, 188, 212));
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        addSpace(root, 22);
        TextView title = title("ЭЛИАС\nОБЩЕСТВОЗНАНИЕ 10");
        root.addView(title);
        TextView subtitle = text("Командная игра для повторения терминов: объясняй слово, не называя его, и набирай очки.", 17, Color.WHITE, false);
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle);
        addSpace(root, 18);
        root.addView(menuButton("▶ Начать игру", Color.rgb(255, 193, 7), Color.rgb(74, 20, 140), v -> { play(soundClick); resetScores(); startRoundIntro(); }));
        root.addView(menuButton("👥 Команды", Color.rgb(255, 64, 129), Color.WHITE, v -> { play(soundClick); showTeamsMenu(); }));
        root.addView(menuButton("⚡ Уровень: " + difficultyName(currentDifficulty), Color.rgb(0, 230, 118), Color.rgb(0, 77, 64), v -> { play(soundClick); showDifficultyMenu(); }));
        root.addView(menuButton("📚 Словарь карточек", Color.rgb(41, 121, 255), Color.WHITE, v -> { play(soundClick); showDictionary(); }));
        root.addView(menuButton("❔ Правила", Color.rgb(255, 112, 67), Color.WHITE, v -> { play(soundClick); showRules(); }));
        addSpace(root, 18);
        TextView footer = text("Сейчас: " + teamCount + " команды, " + cardsForDifficulty() + " карточек в выбранном уровне", 14, Color.WHITE, false);
        footer.setGravity(Gravity.CENTER);
        root.addView(footer);
        setContentView(root);
    }

    private void showTeamsMenu() {
        LinearLayout content = baseLayout(Color.rgb(63, 81, 181), Color.rgb(233, 30, 99));
        content.addView(screenHeader("Настройка команд", "Можно играть от 2 до 6 команд. Названия сохраняются до закрытия приложения."));

        LinearLayout controls = horizontal();
        Button minus = smallButton("−", Color.rgb(255, 193, 7), Color.rgb(74, 20, 140));
        TextView count = text(String.valueOf(teamCount), 26, Color.WHITE, true);
        count.setGravity(Gravity.CENTER);
        Button plus = smallButton("+", Color.rgb(255, 193, 7), Color.rgb(74, 20, 140));
        controls.addView(minus, new LinearLayout.LayoutParams(0, dp(54), 1));
        controls.addView(count, new LinearLayout.LayoutParams(0, dp(54), 1));
        controls.addView(plus, new LinearLayout.LayoutParams(0, dp(54), 1));
        content.addView(controls);
        addSpace(content, 14);

        LinearLayout inputBox = verticalCard(Color.WHITE, 18);
        teamInputs.clear();
        for (int i = 0; i < teamCount; i++) {
            EditText input = new EditText(this);
            input.setText(teamNames.get(i));
            input.setSingleLine(true);
            input.setTextSize(18);
            input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18)});
            input.setHint("Команда " + (i + 1));
            input.setPadding(dp(12), dp(8), dp(12), dp(8));
            teamInputs.add(input);
            inputBox.addView(input, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(58)));
        }
        content.addView(inputBox);

        minus.setOnClickListener(v -> {
            play(soundClick);
            saveTeamNamesFromInputs();
            if (teamCount > 2) {
                teamCount--;
                trimTeams();
                scores = new int[teamCount];
                showTeamsMenu();
            }
        });
        plus.setOnClickListener(v -> {
            play(soundClick);
            saveTeamNamesFromInputs();
            if (teamCount < 6) {
                teamCount++;
                while (teamNames.size() < teamCount) teamNames.add("Команда " + (teamNames.size() + 1));
                scores = new int[teamCount];
                showTeamsMenu();
            }
        });

        content.addView(menuButton("Сохранить команды", Color.rgb(0, 230, 118), Color.rgb(0, 77, 64), v -> {
            play(soundCorrect);
            saveTeamNamesFromInputs();
            scores = new int[teamCount];
            Toast.makeText(this, "Команды сохранены", Toast.LENGTH_SHORT).show();
            showMainMenu();
        }));
        content.addView(menuButton("Назад", Color.rgb(255, 255, 255), Color.rgb(74, 20, 140), v -> { play(soundClick); showMainMenu(); }));
        setContentView(wrapScroll(content));
    }

    private void trimTeams() {
        while (teamNames.size() > teamCount) teamNames.remove(teamNames.size() - 1);
    }

    private void saveTeamNamesFromInputs() {
        for (int i = 0; i < teamInputs.size() && i < teamNames.size(); i++) {
            String value = teamInputs.get(i).getText().toString().trim();
            if (value.length() == 0) value = "Команда " + (i + 1);
            teamNames.set(i, value);
        }
    }

    private void showDifficultyMenu() {
        LinearLayout root = baseLayout(Color.rgb(0, 150, 136), Color.rgb(103, 58, 183));
        root.addView(screenHeader("Выбор сложности", "Уровень влияет на набор карточек: чем выше уровень, тем больше абстрактных терминов."));
        root.addView(difficultyCard(EASY, "Лёгкий", "базовые понятия: общество, право, рынок, культура, семья"));
        root.addView(difficultyCard(MEDIUM, "Средний", "право, политика, экономика, социальная структура"));
        root.addView(difficultyCard(HARD, "Сложный", "термины для сильных учеников и подготовки к проверочной работе"));
        root.addView(menuButton("Назад", Color.WHITE, Color.rgb(74, 20, 140), v -> { play(soundClick); showMainMenu(); }));
        setContentView(root);
    }

    private View difficultyCard(final int difficulty, String name, String description) {
        LinearLayout card = verticalCard(difficulty == currentDifficulty ? Color.rgb(255, 236, 179) : Color.WHITE, 18);
        TextView t = text((difficulty == currentDifficulty ? "✓ " : "") + name, 22, Color.rgb(74, 20, 140), true);
        TextView d = text(description, 16, Color.rgb(70, 70, 70), false);
        card.addView(t);
        card.addView(d);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(8), 0, dp(8));
        card.setLayoutParams(lp);
        card.setOnClickListener(v -> {
            play(soundCorrect);
            currentDifficulty = difficulty;
            showDifficultyMenu();
        });
        return card;
    }

    private void startRoundIntro() {
        stopTimer();
        LinearLayout root = baseLayout(Color.rgb(33, 150, 243), Color.rgb(156, 39, 176));
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        addSpace(root, 18);
        root.addView(title("Ход команды"));
        root.addView(text(teamNames.get(currentTeam), 32, Color.WHITE, true));
        addSpace(root, 10);
        root.addView(scoreBoard());
        addSpace(root, 18);
        TextView rules = text("За 60 секунд объясняйте термины по обществознанию. За каждое угаданное слово команда получает 1 очко. Победа — " + TARGET_SCORE + " очков.", 18, Color.WHITE, false);
        rules.setGravity(Gravity.CENTER);
        root.addView(rules);
        addSpace(root, 22);
        root.addView(menuButton("Начать раунд", Color.rgb(255, 193, 7), Color.rgb(74, 20, 140), v -> { play(soundClick); startRound(); }));
        root.addView(menuButton("В меню", Color.WHITE, Color.rgb(74, 20, 140), v -> { play(soundClick); showMainMenu(); }));
        setContentView(root);
    }

    private void startRound() {
        prepareDeck();
        timeLeft = ROUND_SECONDS;
        LinearLayout root = baseLayout(Color.rgb(255, 112, 67), Color.rgb(123, 31, 162));
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        teamView = text(teamNames.get(currentTeam), 24, Color.WHITE, true);
        teamView.setGravity(Gravity.CENTER);
        root.addView(teamView);
        timerView = text("01:00", 34, Color.rgb(255, 235, 59), true);
        timerView.setGravity(Gravity.CENTER);
        root.addView(timerView);
        scoreView = text("Очки: " + scores[currentTeam], 18, Color.WHITE, true);
        scoreView.setGravity(Gravity.CENTER);
        root.addView(scoreView);
        addSpace(root, 10);

        LinearLayout card = verticalCard(Color.WHITE, 26);
        card.setMinimumHeight(dp(230));
        card.setGravity(Gravity.CENTER);
        cardTermView = text("", 34, Color.rgb(74, 20, 140), true);
        cardTermView.setGravity(Gravity.CENTER);
        cardHintView = text("", 17, Color.rgb(80, 80, 80), false);
        cardHintView.setGravity(Gravity.CENTER);
        card.addView(cardTermView);
        addSpace(card, 10);
        card.addView(cardHintView);
        root.addView(card, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addSpace(root, 18);
        LinearLayout buttons = horizontal();
        Button skip = actionButton("Пропуск", Color.rgb(255, 82, 82), Color.WHITE);
        Button correct = actionButton("Угадали +1", Color.rgb(0, 200, 83), Color.WHITE);
        buttons.addView(skip, new LinearLayout.LayoutParams(0, dp(74), 1));
        buttons.addView(correct, new LinearLayout.LayoutParams(0, dp(74), 1));
        root.addView(buttons);
        addSpace(root, 8);
        root.addView(menuButton("Завершить раунд", Color.WHITE, Color.rgb(74, 20, 140), v -> { play(soundTimerEnd); showRoundEnd(); }));
        skip.setOnClickListener(v -> { play(soundSkip); nextCard(); });
        correct.setOnClickListener(v -> {
            play(soundCorrect);
            scores[currentTeam]++;
            scoreView.setText("Очки: " + scores[currentTeam]);
            if (scores[currentTeam] >= TARGET_SCORE) {
                showGameOver();
            } else {
                nextCard();
            }
        });
        setContentView(root);
        nextCard();
        startTimer();
    }

    private void prepareDeck() {
        roundDeck.clear();
        for (Card c : cards) {
            if (c.difficulty <= currentDifficulty) roundDeck.add(c);
        }
        Collections.shuffle(roundDeck, random);
        cardIndex = 0;
    }

    private void nextCard() {
        if (roundDeck.isEmpty()) prepareDeck();
        if (cardIndex >= roundDeck.size()) {
            Collections.shuffle(roundDeck, random);
            cardIndex = 0;
        }
        Card c = roundDeck.get(cardIndex++);
        if (cardTermView != null) cardTermView.setText(c.term);
        if (cardHintView != null) cardHintView.setText("Подсказка ведущему: " + c.hint);
    }

    private void startTimer() {
        stopTimer();
        timer = new CountDownTimer(ROUND_SECONDS * 1000L, 1000L) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) Math.ceil(millisUntilFinished / 1000.0);
                if (timerView != null) timerView.setText(String.format(Locale.getDefault(), "00:%02d", timeLeft));
                if (timeLeft <= 5) play(soundClick);
            }

            @Override
            public void onFinish() {
                play(soundTimerEnd);
                showRoundEnd();
            }
        };
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void showRoundEnd() {
        stopTimer();
        if (scores[currentTeam] >= TARGET_SCORE) {
            showGameOver();
            return;
        }
        LinearLayout root = baseLayout(Color.rgb(76, 175, 80), Color.rgb(33, 150, 243));
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.addView(title("Раунд завершён"));
        root.addView(text(teamNames.get(currentTeam) + ": " + scores[currentTeam] + " очков", 25, Color.WHITE, true));
        addSpace(root, 12);
        root.addView(scoreBoard());
        currentTeam = (currentTeam + 1) % teamCount;
        addSpace(root, 16);
        root.addView(menuButton("Следующая команда: " + teamNames.get(currentTeam), Color.rgb(255, 193, 7), Color.rgb(74, 20, 140), v -> { play(soundClick); startRoundIntro(); }));
        root.addView(menuButton("В меню", Color.WHITE, Color.rgb(74, 20, 140), v -> { play(soundClick); showMainMenu(); }));
        setContentView(root);
    }

    private void showGameOver() {
        stopTimer();
        play(soundTimerEnd);
        LinearLayout root = baseLayout(Color.rgb(255, 193, 7), Color.rgb(233, 30, 99));
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        addSpace(root, 20);
        root.addView(title("Победа!"));
        TextView winner = text(teamNames.get(currentTeam), 34, Color.WHITE, true);
        winner.setGravity(Gravity.CENTER);
        root.addView(winner);
        addSpace(root, 12);
        root.addView(scoreBoard());
        addSpace(root, 22);
        root.addView(menuButton("Новая игра", Color.rgb(0, 230, 118), Color.rgb(0, 77, 64), v -> { play(soundCorrect); resetScores(); startRoundIntro(); }));
        root.addView(menuButton("В меню", Color.WHITE, Color.rgb(74, 20, 140), v -> { play(soundClick); showMainMenu(); }));
        setContentView(root);
    }

    private void resetScores() {
        scores = new int[teamCount];
        currentTeam = 0;
    }

    private View scoreBoard() {
        LinearLayout box = verticalCard(Color.argb(235, 255, 255, 255), 18);
        TextView header = text("Счёт", 22, Color.rgb(74, 20, 140), true);
        header.setGravity(Gravity.CENTER);
        box.addView(header);
        for (int i = 0; i < teamCount; i++) {
            TextView row = text(teamNames.get(i) + " — " + scores[i], 18, i == currentTeam ? Color.rgb(216, 27, 96) : Color.rgb(60, 60, 60), i == currentTeam);
            row.setPadding(0, dp(4), 0, dp(4));
            box.addView(row);
        }
        return box;
    }

    private void showRules() {
        LinearLayout root = baseLayout(Color.rgb(0, 121, 107), Color.rgb(74, 20, 140));
        root.addView(screenHeader("Правила", "Игра подходит для повторения темы перед самостоятельной, зачётом или уроком-обобщением."));
        LinearLayout card = verticalCard(Color.WHITE, 20);
        card.addView(text("1. Команда выбирает ведущего на раунд.\n\n2. Ведущий видит термин и объясняет его одноклассникам, не называя само слово и прямые однокоренные формы.\n\n3. Если команда угадала термин, нажмите «Угадали +1». Если карточка слишком сложная, нажмите «Пропуск».\n\n4. После 60 секунд ход переходит следующей команде.\n\n5. Побеждает команда, которая первой набирает " + TARGET_SCORE + " очков.\n\nРекомендация для урока: лёгкий уровень использовать для разминки, средний — для основной игры, сложный — для сильных групп и повторения перед контрольной.", 17, Color.rgb(40, 40, 40), false));
        root.addView(card);
        root.addView(menuButton("Назад", Color.WHITE, Color.rgb(74, 20, 140), v -> { play(soundClick); showMainMenu(); }));
        setContentView(wrapScroll(root));
    }

    private void showDictionary() {
        LinearLayout root = baseLayout(Color.rgb(25, 118, 210), Color.rgb(123, 31, 162));
        root.addView(screenHeader("Словарь карточек", "Всего в приложении " + cards.size() + " терминов. В игре выбранный уровень добавляет карточки от простых к сложным."));
        root.addView(dictionarySection("Лёгкий уровень", EASY));
        root.addView(dictionarySection("Средний уровень", MEDIUM));
        root.addView(dictionarySection("Сложный уровень", HARD));
        root.addView(menuButton("Назад", Color.WHITE, Color.rgb(74, 20, 140), v -> { play(soundClick); showMainMenu(); }));
        setContentView(wrapScroll(root));
    }

    private View dictionarySection(String name, int level) {
        LinearLayout box = verticalCard(Color.WHITE, 18);
        TextView header = text(name, 22, Color.rgb(74, 20, 140), true);
        box.addView(header);
        for (Card c : cards) {
            if (c.difficulty == level) {
                TextView row = text("• " + c.term + " — " + c.hint, 15, Color.rgb(50, 50, 50), false);
                row.setPadding(0, dp(3), 0, dp(3));
                box.addView(row);
            }
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(8), 0, dp(8));
        box.setLayoutParams(lp);
        return box;
    }

    private int cardsForDifficulty() {
        int count = 0;
        for (Card c : cards) if (c.difficulty <= currentDifficulty) count++;
        return count;
    }

    private String difficultyName(int value) {
        if (value == EASY) return "лёгкий";
        if (value == HARD) return "сложный";
        return "средний";
    }

    private LinearLayout baseLayout(int colorStart, int colorEnd) {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(18), dp(18), dp(18));
        GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[]{colorStart, colorEnd});
        root.setBackground(bg);
        return root;
    }

    private ScrollView wrapScroll(View child) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(false);
        scroll.addView(child);
        return scroll;
    }

    private TextView title(String value) {
        TextView t = text(value, 36, Color.WHITE, true);
        t.setGravity(Gravity.CENTER);
        t.setTypeface(Typeface.DEFAULT_BOLD);
        t.setLetterSpacing(0.04f);
        t.setShadowLayer(4, 0, 2, Color.argb(120, 0, 0, 0));
        return t;
    }

    private View screenHeader(String main, String sub) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setGravity(Gravity.CENTER_HORIZONTAL);
        box.addView(title(main));
        TextView subtitle = text(sub, 16, Color.WHITE, false);
        subtitle.setGravity(Gravity.CENTER);
        box.addView(subtitle);
        addSpace(box, 16);
        return box;
    }

    private TextView text(String value, int sp, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(sp);
        t.setTextColor(color);
        t.setLineSpacing(0, 1.1f);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private Button menuButton(String label, int bgColor, int textColor, View.OnClickListener listener) {
        Button b = actionButton(label, bgColor, textColor);
        b.setTextSize(19);
        b.setAllCaps(false);
        b.setOnClickListener(listener);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(60));
        lp.setMargins(0, dp(7), 0, dp(7));
        b.setLayoutParams(lp);
        return b;
    }

    private Button smallButton(String label, int bgColor, int textColor) {
        Button b = actionButton(label, bgColor, textColor);
        b.setTextSize(26);
        b.setAllCaps(false);
        return b;
    }

    private Button actionButton(String label, int bgColor, int textColor) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextColor(textColor);
        b.setTypeface(Typeface.DEFAULT_BOLD);
        b.setAllCaps(false);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bgColor);
        gd.setCornerRadius(dp(22));
        b.setBackground(gd);
        b.setPadding(dp(8), dp(6), dp(8), dp(6));
        b.setElevation(dp(5));
        return b;
    }

    private LinearLayout verticalCard(int color, int paddingDp) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(paddingDp), dp(paddingDp), dp(paddingDp), dp(paddingDp));
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(color);
        bg.setCornerRadius(dp(24));
        card.setBackground(bg);
        card.setElevation(dp(6));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, dp(8), 0, dp(8));
        card.setLayoutParams(lp);
        return card;
    }

    private LinearLayout horizontal() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setPadding(0, dp(4), 0, dp(4));
        return row;
    }

    private void addSpace(LinearLayout root, int dp) {
        Space s = new Space(this);
        root.addView(s, new LinearLayout.LayoutParams(1, dp(dp)));
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
    }

    private static class Card {
        final int difficulty;
        final String term;
        final String hint;

        Card(int difficulty, String term, String hint) {
            this.difficulty = difficulty;
            this.term = term;
            this.hint = hint;
        }
    }
}
