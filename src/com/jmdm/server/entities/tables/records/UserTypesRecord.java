/**
 * This class is generated by jOOQ
 */
package com.jmdm.server.entities.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.3.2" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserTypesRecord extends org.jooq.impl.UpdatableRecordImpl<com.jmdm.server.entities.tables.records.UserTypesRecord> implements org.jooq.Record2<java.lang.Integer, java.lang.String> {

	private static final long serialVersionUID = 1206117573;

	/**
	 * Setter for <code>user_types.id</code>.
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>user_types.id</code>.
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>user_types.type_name</code>.
	 */
	public void setTypeName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>user_types.type_name</code>.
	 */
	public java.lang.String getTypeName() {
		return (java.lang.String) getValue(1);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record2 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Integer, java.lang.String> fieldsRow() {
		return (org.jooq.Row2) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row2<java.lang.Integer, java.lang.String> valuesRow() {
		return (org.jooq.Row2) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return com.jmdm.server.entities.tables.UserTypes.USER_TYPES.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return com.jmdm.server.entities.tables.UserTypes.USER_TYPES.TYPE_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getTypeName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserTypesRecord value1(java.lang.Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserTypesRecord value2(java.lang.String value) {
		setTypeName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserTypesRecord values(java.lang.Integer value1, java.lang.String value2) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserTypesRecord
	 */
	public UserTypesRecord() {
		super(com.jmdm.server.entities.tables.UserTypes.USER_TYPES);
	}

	/**
	 * Create a detached, initialised UserTypesRecord
	 */
	public UserTypesRecord(java.lang.Integer id, java.lang.String typeName) {
		super(com.jmdm.server.entities.tables.UserTypes.USER_TYPES);

		setValue(0, id);
		setValue(1, typeName);
	}
}
