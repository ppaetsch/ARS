package com.uni.ARS.session;

import com.uni.ARS.cards.Answer;
import com.uni.ARS.cards.Evaluation;
import com.uni.ARS.cards.QACard;
import com.uni.ARS.cards.Question;
import com.uni.ARS.user.User;

import java.util.*;

/**
 * Handles all data for the session
 */
public class DataHandler {

    private Map<Integer, Question> questions = new HashMap<>();
    private Map<Integer, Answer> answers = new HashMap<>();
    private Map<Integer, Evaluation> evaluationsAnswer = new HashMap<>();
    private Map<Integer, Evaluation> evaluationsQuestion = new HashMap<>();
    private Map<Integer, Integer> questionToAnswers = new HashMap<>();
    private Map<Integer, Integer> answerToEvaluations = new HashMap<>();
    private Map<Integer, QACard> cards = new HashMap<>();
    private int nextQuestionId = 0;
    private int nextAnswerId = 0;
    private int nextEvaluationAnswerId = 0;
    private int nextEvaluationQuestionId = 0;

    public DataHandler() {
    }

    /**
     * Adds question to questions Map, creates new QACard and returns id
     *
     * @param question the question object, that is inserted
     * @param sessionname name of current session, to assign cards to
     * @return the id of the inserted question
     */
    public Integer insertQuestion(Question question, String sessionname){
        Integer id = nextQuestionId;
        question.setId(id);
        questions.put(id, question);
        QACard qaCardNew = new QACard(question, id, sessionname);
        cards.put(id, qaCardNew);
        nextQuestionId++;
        return id;
    }

    public void updateQuestion(Question question){
        Integer id = question.getId();
        questions.replace(id, question);
    }


    public List<QACard> getAllCardsAsList(){
        return new ArrayList<QACard>(cards.values());
    }

    /**
     * Gets question for user to display and answer. Skips questions user asked or already answered
     *
     * @param user
     * @return question, user hasn't answered yet, null if no unanswered question is found
     */
    public Question getQuestionForUser(User user) {
        Random random = new Random();
        int tries = 0;
        while(tries<30){
            Integer i = random.nextInt(questions.size());
            if(!user.getQuestions().contains(i)&&!user.getAnswers().values().contains(i)){
                Question question = questions.get(i);
                return question;
            }
            tries++;
        }
        return null;
    }

    /**
     * Inserts answer into answer map and inside QACard
     *
     * @param answer answer to be inserted
     * @param qid id of question, which was answered
     * @return the id of the inserted answer
     */
    public Integer insertAnswer(Answer answer, Integer qid){
        Integer id = nextAnswerId;
        answer.setId(id);
        answers.put(id, answer);
        cards.get(qid).getAnswerEvaluationMap().put(answer.getId(),answer);
        questionToAnswers.put(id, qid);
        nextAnswerId++;
        return id;
    }

    /**
     * Gets question and answer for user to evaluate. Skips questions user asked, answered or already evaluated,
     * as well as answers given or already evaluated
     *
     * @param user
     * @return question and answer, user hasn't evaluated yet, null if own question, answered question or already evaluation question/answer is found
     */
    public QACard getDataForEvaluation(User user) {
        if (questionToAnswers.size()!=0){
            Random random = new Random();
            int tries=0;
            while(tries<30){
                Integer i = random.nextInt(questionToAnswers.size());
                Integer qid = questionToAnswers.get(i);
                if(!user.getQuestions().contains(qid)&&!user.getAnswers().values().contains(qid)&&!user.getEvaluationsQuestions().values().contains(qid)&&!user.getAnswers().containsKey(i)&&!user.getEvaluationsAnswers().values().contains(i)){
                    QACard card = cards.get(qid);
                    QACard qaCard = new QACard(card.getQuestion());
                    Answer answer = card.getAnswerEvaluationMap().get(i);
                    qaCard.getAnswerEvaluationMap().put(0,answer);
                    return qaCard;
                }
                tries++;
            }
        }
        return null;
    }

    /**
     * Inserts evaluation in evaluationsAnswer Map
     *
     * @param answerEvaluation evaluation to insert
     * @param questionId id to access corresponding card
     * @param answerId id for answer that was evaluated
     * @return id of inserted evaluation
     */
    public Integer insertEvaluationAnswer(Evaluation answerEvaluation, Integer questionId, Integer answerId) {
        Integer id = nextEvaluationAnswerId;
        answerEvaluation.setId(id);
        evaluationsAnswer.put(id, answerEvaluation);
        cards.get(questionId).getAnswerEvaluationMap().get(answerId).addToEvaluationList(answerEvaluation);
        nextEvaluationAnswerId++;
        return id;
    }

    /**
     * Inserts evaluation in evaluationsQuestion Map
     *
     * @param questionEvaluation evaluation to insert
     * @param questionId id to access corresponding card
     * @return id of inserted evaluation
     */
    public Integer insertEvaluationQuestion(Evaluation questionEvaluation, Integer questionId){
        Integer id = nextEvaluationQuestionId;
        questionEvaluation.setId(id);
        evaluationsQuestion.put(id, questionEvaluation);
        cards.get(questionId).addToQuestionEvaluationMap(questionEvaluation);
        nextEvaluationQuestionId++;
        return id;
    }

    /**
     * Gets all cards for user either asked or answered
     *
     * @param user
     * @return all cards user has asked questions or answered
     */
    public List<QACard> getCardsForUser(User user) {
        List<QACard> card = new ArrayList<>();
        for (Integer userQuestion:user.getQuestions()){
            card.add(cards.get(userQuestion));
        }
        List<Integer> userAnswers = new ArrayList<>(user.getAnswers().values());
        for (Integer userAnswer:userAnswers){
            card.add(cards.get(userAnswer));
        }
        return card;
    }
}
