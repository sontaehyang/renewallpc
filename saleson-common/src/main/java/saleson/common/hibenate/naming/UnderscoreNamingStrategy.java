package saleson.common.hibenate.naming;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.util.Locale;

/**
 * hibernate camel to underscore(Upper)
 *
 * http://jtuts.com/2016/12/30/improvednamingstrategy-does-not-work-with-hibernate-5/
 *
 * ex) userName = USER_NAME
 *
 * jpaProperties
 * hibernate.physical_naming_strategy=saleson.common.hibenate.naming.UnderscoreNamingStrategy로 설정함.
 *
 * @author dbclose
 * @date 2018-04-05
 */
public class UnderscoreNamingStrategy extends PhysicalNamingStrategyStandardImpl {
	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return new Identifier(addUnderscores(name.getText()), name.isQuoted());
	}

	protected static String addUnderscores(String name) {
		final StringBuilder buf = new StringBuilder(name.replace('.', '_'));
		for (int i = 1; i < buf.length() - 1; i++) {
			if (Character.isLowerCase(buf.charAt(i - 1)) &&
					Character.isUpperCase(buf.charAt(i)) &&
					Character.isLowerCase(buf.charAt(i + 1))) {
				buf.insert(i++, '_');
			}
		}
		return buf.toString().toUpperCase(Locale.ROOT);
	}
}
