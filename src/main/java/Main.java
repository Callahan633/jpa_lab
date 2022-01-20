import domain.*;
import services.AddressService;
import org.apache.commons.cli.*;
import services.ChildService;
import services.EducationService;
import services.ParentsService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if (new AddressService().getAddress().size() == 0) {
            System.out.println("mocking data...");
            MockData();
        }

        Options options = new Options();
        Option addParents = new Option("p", "addParents", false, "add parents");
        options.addOption(addParents);
        Option momName = new Option("m", "mom", true, "set mom name");
        options.addOption(momName);
        Option dadName = new Option("d", "dad", true, "set dad name");
        options.addOption(dadName);
        Option setAddress = new Option("a", "setAddress", true, "set address");
        setAddress.setArgs(2);
        options.addOption(setAddress);
        Option setEducational = new Option("e", "setEducational", true, "set educational");
        setEducational.setArgs(2);
        options.addOption(setEducational);
        Option addChild = new Option("c", "addChild", true, "add child");
        addChild.setArgs(3);
        options.addOption(addChild);

        CommandLineParser parser = new org.apache.commons.cli.BasicParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
        }

        if (cmd.hasOption('p')) {
            AddParents(cmd);
        }
        if (cmd.hasOption('c')) {
            AddChild(cmd.getOptionValues('c'));
        }
        if (cmd.hasOption('e')) {
            ChangeEducational(cmd.getOptionValues('e'));
        }
        if (cmd.hasOption('a')) {
            ChangeAddress(cmd.getOptionValues('a'));
        }
    }

    private static void ChangeEducational(String[] args) {
        int childId = Integer.parseInt(args[0]);
        int educationalEstablishmentId = Integer.parseInt(args[1]);
        ChildService childService = new ChildService();
        Child child = childService.getById(childId);
        child.education = new EducationService().getById(educationalEstablishmentId);
        child.update();
    }

    private static void ChangeAddress(String[] args) {
        int parentsId = Integer.parseInt(args[0]);
        int addressId = Integer.parseInt(args[1]);
        ParentsService parentsService = new ParentsService();
        Parents parents = parentsService.getById(parentsId);
        parents.address = new AddressService().getById(addressId);
        parents.update();
    }

    private static void AddChild(String[] args) {
        String fullname = args[0];
        int parentsCoupleId = Integer.parseInt(args[1]);
        int age = Integer.parseInt(args[2]);
        Parents parents = new ParentsService().getById(parentsCoupleId);

        if (parents.address != null) {
            List<Education> educational = new EducationService().getEducation();
            List<Education> fineEducational = educational.stream().filter(x -> parents.address.area.addresses.stream().anyMatch(y -> y.id == x.address.id)).collect(Collectors.toList());
            fineEducational.forEach(x -> System.out.println("Appropriate educational institution. Name: " + x.number + ". Id: " + x.id));
        }

        Child child = new Child(fullname, parents, age);
        child.save();
        System.out.println("Child added. Id " + child.id);
    }

    private static void AddParents(CommandLine args) {
        Parents parents = new Parents();
        if (args.hasOption('m')) {
            String momFullname = args.getOptionValue('m');
            parents.FullnameFirst = momFullname;
        }
        if (args.hasOption('d')) {
            String dadFullname = args.getOptionValue('d');
            parents.FullnameSecond = dadFullname;
        }
        parents.save();
        System.out.println("Parent(s) added. Id " + parents.id);
    }

    public static void MockData() {
        Area[] areas = new Area[]{
                new Area(),
                new Area(),
                new Area(),
        };
        for (Area area : areas) {
            area.Save();
        }
        int i = 0;
        List<Address> addresses = new ArrayList<>(25);
        for (String addressName : new String[]{
                "1 Street",
                "2 Street",
                "3 Street",
                "4 Street",
                "5 Street",
                "6 Street",
                "7 Street",
                "8 Street",
                "9 Street",
                "10 Street",
                "11 Street",
                "12 Street",
                "13 Street",
                "14 Street",
                "15 Street",
                "16 Street",
                "17 Street",
                "18 Street",
                "19 Street",
                "20 Street",
                "21 Street",
                "22 Street",
                "23 Street",
                "24 Street",
                "25 Street",
        }) {
            Address address = new Address(addressName);
            addresses.add(address);
            address.area = areas[i % 3];
            address.Save();
            i++;
        }

        for (i = 0; i < 6; i++) {
            Education education = new Education("School #" + i, addresses.get(i));
            education.Save();
        }
    }
}
