package nl.gremmee.war.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import nl.gremmee.war.ai.AIAttackHighFirst;
import nl.gremmee.war.ai.AIAttackLowFirst;
import nl.gremmee.war.ai.AIAttacker;
import nl.gremmee.war.ai.AIDefender;
import nl.gremmee.war.ai.AIOneByOne;
import nl.gremmee.war.ai.AIOnlyMaxAttack;
import nl.gremmee.war.ai.AISuicide;
import nl.gremmee.war.ai.IAi;

public class AIController {
    private static AIController instance;
    private List<IAi> ais;
    private Random randomGenerator;

    private AIController() {
        ais = new ArrayList<>();
        randomGenerator = new Random();
        initAI();
    }

    public static AIController getInstance() {
        if (instance == null) {
            instance = new AIController();
        }
        return instance;
    }

    public IAi getAI(int aIndex) {
        return ais.get(aIndex);
    }

    public List<IAi> getAis() {
        return ais;
    }

    public IAi getRandomAI() {
        IAi randomAi;
        int randomIndex = randomGenerator.nextInt((ais.size()));
        randomAi = ais.get(randomIndex);
        return randomAi;
    }

    public void initAI() {
        AIAttackHighFirst attackHighFirst = new AIAttackHighFirst();
        ais.add(attackHighFirst);
        AIAttackLowFirst attackLowFirst = new AIAttackLowFirst();
        ais.add(attackLowFirst);
        AIOneByOne oneByOne = new AIOneByOne();
        ais.add(oneByOne);
        AIOnlyMaxAttack onlyMaxAttack = new AIOnlyMaxAttack();
        ais.add(onlyMaxAttack);
        AISuicide suicide = new AISuicide();
        ais.add(suicide);
        AIDefender defender = new AIDefender();
        ais.add(defender);
        AIAttacker attacker = new AIAttacker();
        ais.add(attacker);
    }
}
