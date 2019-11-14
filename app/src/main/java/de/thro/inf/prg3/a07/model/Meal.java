package de.thro.inf.prg3.a07.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the instance of a meal
 */
public class Meal
{
	private int id;
	private String name;
	private String category;
	private List<String> notes;

	/**
	 * Constructor
	 * Initialize notes
	 */
	public Meal()
	{
		notes = new LinkedList<>();
	}

	/**
	 * GETTER : id
	 *
	 * @return ID of the meal
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * GETTER : name
	 *
	 * @return Name of the meal
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * GETTER : category
	 *
	 * @return Category of the meal
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * GETTER : notes
	 *
	 * @return List containing all notes to meal (ingredients)
	 */
	public List<String> getNotes()
	{
		return notes;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;

		if (!(o instanceof Meal)) return false;

		Meal meal = (Meal) o;

		return new EqualsBuilder()
			.append(getId(), meal.getId())
			.append(getName(), meal.getName())
			.append(getCategory(), meal.getCategory())
			.append(getNotes(), meal.getNotes())
			.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder(17, 37)
			.append(getId())
			.append(getName())
			.append(getCategory())
			.append(getNotes())
			.toHashCode();
	}

	@Override
	public String toString()
	{
		StringBuilder notesBuilder = new StringBuilder();

		for (String s : notes)
			notesBuilder.append(String.format("%s, ", s));

		if (notesBuilder.length() > 0)
			notesBuilder.setLength(notesBuilder.length() - 2);

		else
			notesBuilder.append("No notes");

		return String.format("%s\n%s\n%s", name, category, notesBuilder.toString());
	}
}
