/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectmanagement.application.model;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import projectmanagement.application.business.Predecessor;
import projectmanagement.application.business.Project;
import projectmanagement.application.business.Task;
import projectmanagement.ihm.view.MyPopup;
import projectmanagement.ihm.view.MyTabPane;

/**
 *
 * @author Jérémy
 */
public class Diagram {

    private List<String> listMounth = null;
    private final ManagerLanguage managerLang;
    private List<String> days = null;
    private final int space = 5;
    private Task[][] pertdes;
    private int nbcol;
    private int nblig;
    private int placer[][];
    private int decalcol;
    private int nbtache;
    private int colocu[];

    private final Color mesuretemps;
    private final Color pertobjet;
    private final Color fondgantt;
    private final Color fondpert;

    public Diagram() {
        mesuretemps = Color.DARKGRAY;
        pertobjet = DAO.getInstance().getOBJECT_PERT();
        fondgantt = DAO.getInstance().getBACKGROUND_GANTT();
        fondpert = DAO.getInstance().getBACKGROUND_PERT();

        managerLang = ManagerLanguage.getInstance();
        listMounth = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            listMounth.add(managerLang.getLocalizedTexte("month" + i));
        }
        days = new ArrayList<>();
        days.add(managerLang.getLocalizedTexte("day"));
        days.add(managerLang.getLocalizedTexte("days"));
    }

    public AnchorPane showGantt(AnchorPane tabGantt, Project project, MyTabPane pane) {
        System.out.println(project.toStringAll());
        
        if (project.durationProject() == 0) {
            return tabGantt;
        }
        Rectangle fond = new Rectangle();
        fond.setX(0);
        fond.setY(0);
        fond.setWidth(pane.getWidth());
        fond.setHeight(pane.getHeight());
        fond.setFill(fondgantt);
        tabGantt.getChildren().add(fond);
        Rectangle schedule = new Rectangle();
        schedule.setX(0);
        schedule.setY(0);
        schedule.setWidth(pane.getWidth());
        schedule.setHeight(30);
        schedule.setFill(mesuretemps);

        tabGantt.getChildren().add(schedule);
        double interline = pane.getWidth() / project.durationProject();
        //System.err.println(tabGantt.getBoundsInParent().getHeight());
        int daymonth = MyDate.numberDayOfMonth(project.getStart().getDatebegin());
        int day = project.getStart().getDatebegin().day();
        MyDate temp = project.getStart().getDatebegin();
        //System.err.println(temp);
        //System.err.println(project.durationProject());
        if (MyDate.numberDayOfMonth(temp) - day > 2) {
            Text te = new Text();
            te.setText(listMounth.get(temp.getMonth()));
            te.setX(2);
            te.setY(15 - 3);
            tabGantt.getChildren().add(te);
        }
        for (int i = 0; i < project.durationProject(); i++) {
            //System.err.println(daymonth);
            //System.err.println(day);
            Line line = new Line();
            if (daymonth - (day - 1) != 0) {
                line.setStartX(interline * i);
                line.setStartY(15);
            } else {
                line.setStartX(interline * i);
                line.setStartY(5);
                day = 1;
                temp = MyDate.addMonth(temp);
                Text t = new Text();
                t.setText(listMounth.get(temp.getMonth()));
                t.setX(interline * i + 2);
                t.setY(15 - 3);
                tabGantt.getChildren().add(t);
                //System.err.println(temp);
                daymonth = MyDate.numberDayOfMonth(temp) - 1;
            }
            Text chiffre = new Text();
            chiffre.setText(Integer.toString(day));
            chiffre.setX(interline * i + 5);
            chiffre.setY(30 - 3);
            tabGantt.getChildren().add(chiffre);
            line.setEndX(interline * i);
            line.setEndY(30);
            tabGantt.getChildren().add(line);
            day++;
        }
        for (int i = 0; i < project.getTasks().size(); i++) {
            long decal = MyDate.diffDays(project.getStart().getDatebegin(), project.getTasks().get(i).getDatebegin());
            //System.err.println(decal);
            double weight = (double) project.getTasks().get(i).getDuring();
            TaskGantt task = new TaskGantt(project.getTasks().get(i), (int) (weight * interline), (int) (pane.getHeight() / 20));
            task.setLayoutX(interline * decal);
            task.setLayoutY(30 + i * (pane.getHeight() / 20) + (i * space + space));
            tabGantt.getChildren().add(task);
            for (int j = 0; j < project.getTasks().get(i).getPredecessor().size(); j++) {
                for (int k = 0; k < project.getTasks().size(); k++) {
                    if (project.getTasks().get(i).getPredecessor().get(j).getIdTaskParent().equals(
                            project.getTasks().get(k).getId())) {
                        Line line = new Line();
                        line.setStartX(interline * decal);
                        line.setStartY(30 + (i + 0.5) * (pane.getHeight() / 20) + (i * space + space));
                        line.setEndX(interline * decal - (MyDate.diffDays(project.getTasks().get(k).getDateend(), project.getTasks().get(i).getDatebegin())) * interline - 20);
                        line.setEndY(30 + (i + 0.5) * (pane.getHeight() / 20) + (i * space + space));
                        tabGantt.getChildren().add(line);
                        Line lineh = new Line();
                        lineh.setStartX(interline * decal);
                        lineh.setStartY(30 + (i + 0.5) * (pane.getHeight() / 20) + (i * space + space));
                        lineh.setEndX(interline * decal - 5);
                        lineh.setEndY(30 + (i + 0.5) * (pane.getHeight() / 20) - 5 + (i * space + space));
                        tabGantt.getChildren().add(lineh);
                        Line lineb = new Line();
                        lineb.setStartX(interline * decal);
                        lineb.setStartY(30 + (i + 0.5) * (pane.getHeight() / 20) + (i * space + space));
                        lineb.setEndX(interline * decal - 5);
                        lineb.setEndY(30 + (i + 0.5) * (pane.getHeight() / 20) + 5 + (i * space + space));

                        tabGantt.getChildren().add(lineb);

                        Line line2 = new Line();
                        line2.setStartX(interline * decal - (MyDate.diffDays(project.getTasks().get(k).getDateend(), project.getTasks().get(i).getDatebegin())) * interline - 20);
                        line2.setStartY(30 + (i + 0.5) * (pane.getHeight() / 20) + (i * space + space));
                        line2.setEndX(interline * decal - (MyDate.diffDays(project.getTasks().get(k).getDateend(), project.getTasks().get(i).getDatebegin())) * interline - 20);
                        line2.setEndY(30 + (k + 1) * (pane.getHeight() / 20) + (k * space + space));
                        tabGantt.getChildren().add(line2);
                        break;
                    }
                }

            }
        }
        MyDate actu = new MyDate();
        int dateactu = (int) MyDate.diffDays(project.getStart().getDatebegin(), actu);
        Line line = new Line();
        line.setStartX(interline * dateactu);
        line.setStartY(15);
        line.setEndX(interline * dateactu);
        line.setEndY(pane.getHeight() - 15);
        line.setStroke(Color.RED);

        tabGantt.getChildren().add(line);
        return tabGantt;
    }

    public AnchorPane showPert(AnchorPane tabPert, Project project, MyTabPane pane, Stage mainStage) {

        if (project != null) {
            if (isValidate(project, mainStage) == true) {
                tabPert = pert(tabPert, project, pane, mainStage);
            }
        }

        return tabPert;
    }

    public AnchorPane pert(AnchorPane tabPert, Project project, MyTabPane pane, Stage mainStage) {
        //Calculer chemin critique 
        Rectangle fond = new Rectangle();
        fond.setX(0);
        fond.setY(0);
        fond.setWidth(pane.getWidth());
        fond.setHeight(pane.getHeight());
        fond.setFill(fondpert);
        tabPert.getChildren().add(fond);
        nbtache = project.getTasks().size();
        List<Task> vide = new ArrayList<>();
        List<Object> critical = parcours(project.getStart(), project.getEnd(), vide);;
        ArrayList<Task> critique = (ArrayList<Task>) critical.get(0);
        for (int i = 0; i < critique.size(); i++) {
            //System.err.println(critique.get(i).getName());
        }
        nbcol = maxtask(project.getStart(), project.getEnd()).get(0);
        nblig = maxtask(project.getStart(), project.getEnd()).get(1);
        if (nblig % 2 == 0) {
            nblig++;
        }
        pertdes = new Task[nblig][nbcol];
        pertdes[nblig / 2][nbcol - 1] = project.getEnd();
        dessin(project);
        paintPert(decalcol, tabPert, pane, critique);
        return tabPert;
    }

    /* Permet de preparer le pert*/
    public void dessin(Project project) {

        int size = project.getTasks().size();
        placer = new int[size][2];
        for (int i = 0; i < size; i++) {
            placer[i][0] = project.getTasks().get(i).getId();
            placer[i][1] = 0;
        }
        Task temp = project.getEnd();
        pertdes[nblig / 2][nbcol - 1] = project.getEnd();
        colocu = new int[nbcol];
        for (int i = 0; i < nbcol; i++) {
            colocu[i] = 0;
        }
        placer(project.getStart(), project.getEnd(), nbcol - 2);

        decalcol = 0;
        for (int i = 0; i < nbcol; i++) {
            int cpt = 0;
            for (int j = 0; j < nblig; j++) {
                if (pertdes[j][i] == null) {
                    //System.err.print("0 ");
                    cpt++;
                } else {
                    //System.err.print(pertdes[j][i].getId() + " ");
                }
            }
            if (cpt == nblig) {
                decalcol++;
            }
            //System.err.println("");
        }
        boolean nomodif = true;
        while (nomodif != false) {
            nomodif = false;
        }
        for (int i = 0; i < nbcol - 1; i++) {
            for (int j = 0; j < nblig; j++) {
                if (pertdes[j][i] != null && pertdes[j][i + 1] != null && MyDate.diffDays(pertdes[j][i].getDatebegin(), pertdes[j][i + 1].getDateend()) <= 1) {
                    Task tran = pertdes[j][i + 1];
                    pertdes[j][i + 1] = pertdes[j][i];
                    pertdes[j][i] = tran;
                    nomodif = true;
                }
            }
        }

    }

    private boolean isValidate(Project project, Stage mainStage) {
        if (project.getTasks().isEmpty() || project.getStart() == null) {
            return false;
        }
        List<Task> listTemp = new ArrayList<>(project.getTasks());
        Task start = new Task(project.getStart().getId(), project.getStart().getName(),
                new MyDate(project.getStart().getDatebegin().getTime()), new MyDate(project.getStart().getDateend().getTime()),
                project.getStart().getPriority(), project.getStart().getNote(), project.getStart().getIdProject(),
                new ArrayList<>(project.getStart().getRessources()),
                new ArrayList<>(project.getStart().getPredecessor()));

        listTemp.remove(start);
        boolean ok = true;
        for (Task t : listTemp) {
            if (t.getPredecessor().isEmpty()) {
                ok = false;
                if (ManagerShowDiagram.getInstance().isTabPertShow()) {
                    MyPopup.showPopupMessage(ManagerLanguage.getInstance().
                            getLocalizedTexte("writeAllPredecessorToShowPert"), mainStage);
                }
                break;
            }
        }
        if (ok == true) {
            Task end = project.getEnd();
            for (Task t : project.getTasks()) {
                if (!t.getId().equals(end.getId())) {
                    if (end.getDateend().equals(t.getDateend())) {
                        ok = false;
                        if (ManagerShowDiagram.getInstance().isTabPertShow()) {
                            MyPopup.showPopupMessage(ManagerLanguage.getInstance().
                                    getLocalizedTexte("twoTaskEnd"), mainStage);
                        }
                        break;
                    }
                }
            }
        }
        
        if (ok == true && start.getPredecessor() != null
                && start.getPredecessor().isEmpty()) {
            return true;
        } else {
            return false;
        }

    }
    /*Permet de coller les differents objet pour la formation du pert*/

    public void paintPert(int decalcol, AnchorPane tabPert, MyTabPane pane, List<Task> critique) {
        for (int i = 0; i < nblig; i++) {
            for (int j = decalcol; j < nbcol; j++) {
                if (pertdes[i][j] == null) {
                    // System.err.print("0 ");
                } else {
                    // System.err.print(pertdes[i][j].getId() + " ");
                    TaskPert task = new TaskPert(pertdes[i][j], (int) (pane.getWidth() / (nbcol - decalcol + 1)), (int) (pane.getHeight() / (nblig + 1)), isWayCritical(pertdes[i][j], critique));
                    task.setLayoutX((pane.getWidth() / (nbcol - decalcol + 1)) * (j - decalcol) + (pane.getWidth() / (nbcol - decalcol) / (nbcol - decalcol) * (j - decalcol)));
                    task.setLayoutY((pane.getHeight() / (nblig + 1) * i) + (pane.getHeight() / (nblig + 1) / (nblig + 1)) * i);
                    tabPert.getChildren().add(task);
                    for (int p = 0; p < pertdes[i][j].getPredecessor().size(); p++) {
                        Line l = new Line();
                        l.setStartX((pane.getWidth() / (nbcol - decalcol + 1)) * (j - decalcol) + (pane.getWidth() / (nbcol - decalcol) / (nbcol - decalcol) * (j - decalcol)));
                        l.setStartY((pane.getHeight() / (nblig + 1) * i) + (pane.getHeight() / (nblig + 1) / (nblig + 1)) * i + (pane.getHeight() / (nblig + 1)) / 2);
                        int[] tab = searchPlace(pertdes[i][j].getPredecessor().get(p));
                        l.setEndX((pane.getWidth() / (nbcol - decalcol + 1)) * (tab[1] - decalcol) + (pane.getWidth() / (nbcol - decalcol) / (nbcol - decalcol) * (tab[1] - decalcol)) + (pane.getWidth() / (nbcol - decalcol + 1)));
                        l.setEndY((pane.getHeight() / (nblig + 1) * tab[0]) + (pane.getHeight() / (nblig + 1) / (nblig + 1)) * tab[0] + (pane.getHeight() / (nblig + 1)) / 2);
                        //System.err.println("true && true");
                        //System.err.println(isWayCritical(pertdes[i][j], critique));
                        //System.err.println(isWayCritical(DAO.getInstance().getTask(pertdes[i][j].getPredecessor().get(p).getIdTaskParent()), critique));
                        if (isWayCritical(pertdes[i][j], critique) && isWayCritical(getTask(pertdes[i][j].getPredecessor().get(p).getIdTaskParent()), critique)) {
                            l.setStyle("-fx-background-color:" + pertobjet);
                            l.setStrokeWidth(5);
                        }
                        tabPert.getChildren().add(l);

                    }
                }
            }
            // System.err.println("");
        }

    }

    /*Permet de savoir si une tache appartient au chemin critique*/
    public boolean isWayCritical(Task task, List<Task> critique) {
        for (int i = 0; i < critique.size(); i++) {
            if (task.getId().equals(critique.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

    /*Permet de connaitre une position d'une tache*/
    private int[] searchPlace(Predecessor task) {
        for (int i = 0; i < nblig; i++) {
            for (int j = 0; j < nbcol; j++) {
                if (pertdes[i][j] != null && task.getIdTaskParent().equals(pertdes[i][j].getId())) {
                    int tab[] = new int[2];
                    tab[0] = i;
                    tab[1] = j;
                    return tab;
                }
            }
        }
        return null;
    }

    /*Permet d'assigner une place a une tache*/
    public void placerTask(Task task) {
        //  System.err.println("placer" + task.getId());
        for (int i = 0; i < nbtache; i++) {
            if (placer[i][0] == task.getId()) {
                placer[i][1] = 1;
            }
        }
    }

    /*Permet de savoir si une tache a deja une position assigner*/
    public boolean isPlacer(Task task) {
        //  System.err.println("isplacer" + task.getId());
        for (int i = 0; i < nbtache; i++) {
            if (placer[i][0] == task.getId() && placer[i][1] == 1) {
                return true;
            }
        }
        return false;
    }

    /*Permet de savoir le nombre de colone et de ligne sont nécessaire a l'affichage*/
    public ArrayList<Integer> maxtask(Task start, Task end) {
        ArrayList<Integer> feat = new ArrayList<>();
        //  System.err.println(start.getName() + " à " + end.getName());
        int width = 1;
        int height = 1;

        if (start.getId().equals(end.getId())) {
            width = 1;
            feat.add(width);
            feat.add(height);
            return feat;
        }
        if (end.getPredecessor().size() > height) {
            height = end.getPredecessor().size();
        }
        for (int i = 0; i < end.getPredecessor().size(); i++) {//penser a tester que cela soit bien Idtask et pas idtaskparent
            Predecessor link = end.getPredecessor().get(i);
            //System.err.println(link.getIdTaskParent());
            Task temp = getTask(link.getIdTaskParent());
            if (temp != null) {
                if (temp.getLastdate().before(end.getDatebegin())) {
                    temp.setLastdate(end.getDatebegin());
                }
                ArrayList<Integer> result = maxtask(start, temp);
                if (width < (result.get(0) + 1)) {
                    width = width + result.get(0) + 1;
                }
            }
        }
        feat.add(width);
        feat.add(height);
        return feat;

    }

    /*Permet de placer les differents objets sur la grille */
    public void placer(Task start, Task end, int col) {
        placerTask(end);
        int ts = 1;
        for (int i = 0; i < end.getPredecessor().size(); i++) {
            Predecessor link = end.getPredecessor().get(i);
            Task temp = getTask(link.getIdTaskParent());
            if (isPlacer(temp) == false) {
                if (end.getPredecessor().size() % 2 == 0) {
                    int decal = (nblig - end.getPredecessor().size() + 1) / 2;
                    if (decal + i == nblig / 2) {
                        decal++;
                    }
                    pertdes[decal + i][col] = temp;
                    //System.err.println(temp.getId() + " placé en " + (decal + i) + " " + col);
                } else {
                    int decal = (nblig - end.getPredecessor().size()) / 2;
                    pertdes[decal + i][col] = temp;
                    // System.err.println(temp.getId() + " placé en " + (decal + i) + " " + col);
                }
                colocu[col] = 1;

                while (colocu[col - ts] == 1) {
                    ts++;
                }
                placer(start, temp, col - ts);
            }
        }
    }

    /*Permet de trouver le chemin critique en pacourant les taches de façon recursive*/
    public List<Object> parcours(Task start, Task end, List<Task> critical) {
        //System.err.println(start.getName() + " à " + end.getName());
        int somme = 0;
        int sommePrio = 0;
        List<Object> retour = new ArrayList<>();
        if (start.getId().equals(end.getId())) {
            critical.add(end);
            sommePrio = sommePrio + end.getPriority();
            retour.add(critical);
            retour.add(somme);
            retour.add(sommePrio);
            return retour;
        }
        for (int i = 0; i < end.getPredecessor().size(); i++) {//penser a tester que cela soit bien Idtask et pas idtaskparent
            Predecessor link = end.getPredecessor().get(i);
            //System.err.println(link.getIdTaskParent());
            Task temp = getTask(link.getIdTaskParent());
            List<Task> chemin = new ArrayList<>();
            List<Object> result = parcours(start, temp, chemin);
            int timebetw = (int) MyDate.diffDays(temp.getDateend(), end.getDatebegin());
            if ((somme < (int) result.get(1) + end.getDuring() + timebetw) || (somme == (int) result.get(1) + end.getDuring() + timebetw && sommePrio < (int) result.get(2) + end.getPriority())) {
                critical = (List<Task>) result.get(0);
                somme = (int) result.get(1) + end.getDuring() + timebetw;
                sommePrio = (int) result.get(2) + end.getPriority();
            }
        }
        sommePrio = sommePrio + end.getPriority();
        critical.add(end);
        retour.add(critical);
        retour.add(somme);
        retour.add(sommePrio);
        return retour;
    }

    private Task getTask(Integer id) {
        Task temp = null;
        for (Task t : DAO.getInstance().getCurrentProject().getTasks()) {
            if (id.equals(t.getId())) {
                temp = t;
            }
        }
        return temp;
    }
}
