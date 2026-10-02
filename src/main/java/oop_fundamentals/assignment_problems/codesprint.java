import java.util.*;

enum TrackType {
    INNOVATION,
    OPEN
}

interface ScoringRule {
    double calculateFinalScore(double idea, double execution, double presentation);
}

class InnovationScoringRule implements ScoringRule {
    @Override
    public double calculateFinalScore(double idea, double execution, double presentation) {
        return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
    }
}

class OpenScoringRule implements ScoringRule {
    @Override
    public double calculateFinalScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Project {
    private String title;

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

class Score {
    private double idea;
    private double execution;
    private double presentation;

    public Score(double idea, double execution, double presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    public double getIdea() {
        return idea;
    }

    public double getExecution() {
        return execution;
    }

    public double getPresentation() {
        return presentation;
    }

    public void setIdea(double idea) {
        this.idea = idea;
    }
}

class Team {
    private String name;
    private List<Student> members;
    private TrackType track;
    private ScoringRule scoringRule;
    private Project project;
    private Score score;

    public Team(String name, List<Student> members, TrackType track, ScoringRule scoringRule) {
        this.name = name;
        this.members = members;
        this.track = track;
        this.scoringRule = scoringRule;
    }

    public String getName() {
        return name;
    }

    public List<Student> getMembers() {
        return members;
    }

    public TrackType getTrack() {
        return track;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public double getFinalScore() {
        if (score == null) return 0.0;
        return scoringRule.calculateFinalScore(score.getIdea(), score.getExecution(), score.getPresentation());
    }
}

class Hackathon {
    private Set<String> registeredStudents = new HashSet<>();
    private List<Team> teams = new ArrayList<>();
    private boolean isPublished = false;

    public boolean registerTeam(String teamName, List<Student> members, TrackType track, ScoringRule scoringRule) {
        if (members.size() < 2 || members.size() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return false;
        }

        for (Student s : members) {
            if (registeredStudents.contains(s.getName())) {
                System.out.println("Registration failed: Student " + s.getName() + " is already in a team.");
                return false;
            }
        }

        for (Student s : members) {
            registeredStudents.add(s.getName());
        }

        Team team = new Team(teamName, members, track, scoringRule);
        teams.add(team);
        System.out.printf("Team %s registered (%d members, %s track).%n", 
            teamName, members.size(), track.toString().charAt(0) + track.toString().substring(1).toLowerCase());
        return true;
    }

    public void submitProject(Team team, String projectTitle) {
        if (isPublished) {
            System.out.println("Submission failed: Results have already been published.");
            return;
        }
        Project project = new Project(projectTitle);
        team.setProject(project);
        System.out.printf("Project '%s' submitted by %s.%n", projectTitle, team.getName());
    }

    public void scoreProject(Team team, double idea, double execution, double presentation) {
        if (isPublished) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }
        Score score = new Score(idea, execution, presentation);
        team.setScore(score);
        System.out.printf("Score recorded for '%s'. Final score: %.2f.%n", team.getProject().getTitle(), team.getFinalScore());
    }

    public void publishResults() {
        isPublished = true;
        System.out.println("Results published.");
    }

    public boolean isPublished() {
        return isPublished;
    }
}

public class codesprint {
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon();

        List<Student> members1 = Arrays.asList(new Student("Asha"), new Student("Ravi"), new Student("Neha"));
        hackathon.registerTeam("ByteBusters", members1, TrackType.INNOVATION, new InnovationScoringRule());

        List<Student> members2 = Collections.singletonList(new Student("Kiran"));
        hackathon.registerTeam("SoloCoder", members2, TrackType.OPEN, new OpenScoringRule());

        Team byteBusters = null;
        for (Team t : new Team[]{new Team("ByteBusters", members1, TrackType.INNOVATION, new InnovationScoringRule())}) {
            byteBusters = t;
        }

        hackathon.submitProject(byteBusters, "SmartAttend");
        hackathon.scoreProject(byteBusters, 8, 7, 9);
        hackathon.publishResults();
        hackathon.scoreProject(byteBusters, 10, 7, 9);
    }
}