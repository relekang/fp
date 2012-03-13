/*
 * Created on Oct 22, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package no.ntnu.fp.model;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.ParsingException;

/**
 * @author tho
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class XmlSerializer {
	
	public static final String PERSON = "person";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String GENDER = "gender";
	public static final String DATE_OF_BIRTH = "date-of-birth";

	public Document toXml(Workgroup aProject) {
		Element root = new Element("project");
		
		Iterator it = aProject.iterator();
		while (it.hasNext()) {
			Employee aPerson = (Employee)it.next();
			Element element = personToXml(aPerson);
			root.appendChild(element);
		}
		
		return new Document(root);
	}
	
	public Workgroup toProject(Document xmlDocument) throws ParseException {
		Workgroup aProject = new Workgroup();
		Element groupElement = xmlDocument.getRootElement();
		Elements personElements = groupElement.getChildElements("person");
		
		for (int i = 0; i < personElements.size(); i++) {
			Element childElement = personElements.get(i);
			aProject.addPerson(assemblePerson(childElement));
		}
		
		return aProject;
	}

    public Employee toPerson(String xml) throws java.io.IOException, java.text.ParseException, nu.xom.ParsingException {
	nu.xom.Builder parser = new nu.xom.Builder(false);
	nu.xom.Document doc = parser.build(xml, "");
	return assemblePerson(doc.getRootElement());
    }
	
	private Element personToXml(Employee aPerson) {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		
		Element element = new Element(PERSON);
		Element name = new Element(NAME);
		Element gender = new Element(GENDER);
		Element email = new Element(EMAIL);
		Element dateOfBirth = new Element(DATE_OF_BIRTH);

		name.appendChild(aPerson.getName());
		gender.appendChild("" + aPerson.getGender());
		email.appendChild(aPerson.getEmail());
		dateOfBirth.appendChild(format.format(aPerson.getDateOfBirth()));
		
		element.appendChild(name);
		element.appendChild(gender);
		element.appendChild(email);
		element.appendChild(dateOfBirth);
		return element;
	}
	
	private Employee assemblePerson(Element personElement) throws ParseException {
		String name = null, email = null;
		Employee.Gender gender = null;
		Date date = null;
		Element element = personElement.getFirstChildElement(NAME);
		if (element != null) {
			name = element.getValue();
		}
		element = personElement.getFirstChildElement(GENDER);
		if(element != null) {
			gender = element.getValue().equals("" + Employee.Gender.MALE) 
					? Employee.Gender.MALE : Employee.Gender.FEMALE;
		}
		element = personElement.getFirstChildElement(EMAIL);
		if (element != null) {
			email = element.getValue();
		}
		element = personElement.getFirstChildElement(DATE_OF_BIRTH);
		if (element != null) {
			date = parseDate(element.getValue());
		}
		return new Employee(name, email, date, gender);
	}
	
	/**
	 * TODO: handle this one to avoid duplicate code
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	private Date parseDate(String date) throws ParseException {
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, java.util.Locale.US);
		return format.parse(date);
	}

}

