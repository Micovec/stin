package cz.tul.fm.jiri_vokrinek.stin_semestral.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SQLiteDialectTest {

    @Test
    public void testConstructor() {
        Assertions.assertDoesNotThrow(SQLiteDialect::new);
    }

    @Test
    public void testGettersNotThrows() {
        SQLiteDialect dialect = new SQLiteDialect();

        Assertions.assertDoesNotThrow(dialect::supportsIdentityColumns);
        Assertions.assertDoesNotThrow(dialect::hasDataTypeInIdentityColumn);
        Assertions.assertDoesNotThrow(dialect::getIdentityColumnString);
        Assertions.assertDoesNotThrow(dialect::getIdentitySelectString);
        Assertions.assertDoesNotThrow(dialect::supportsTemporaryTables);
        Assertions.assertDoesNotThrow(dialect::getCreateTemporaryTableString);
        Assertions.assertDoesNotThrow(dialect::dropTemporaryTableAfterUse);
        Assertions.assertDoesNotThrow(dialect::supportsCurrentTimestampSelection);
        Assertions.assertDoesNotThrow(dialect::isCurrentTimestampSelectStringCallable);
        Assertions.assertDoesNotThrow(dialect::getCurrentTimestampSelectString);
        Assertions.assertDoesNotThrow(dialect::supportsUnionAll);
        Assertions.assertDoesNotThrow(dialect::hasAlterTable);
        Assertions.assertDoesNotThrow(dialect::dropConstraints);
        Assertions.assertDoesNotThrow(dialect::getAddColumnString);
        Assertions.assertDoesNotThrow(() -> dialect.getForUpdateString());
        Assertions.assertDoesNotThrow(dialect::supportsOuterJoinForUpdate);
        Assertions.assertDoesNotThrow(dialect::supportsIfExistsBeforeTableName);
        Assertions.assertDoesNotThrow(dialect::supportsCascadeDelete);
    }

    @Test
    public void testGettersThrow() {
        SQLiteDialect dialect = new SQLiteDialect();

        Assertions.assertThrows(UnsupportedOperationException.class, dialect::getDropForeignKeyString);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> dialect.getAddForeignKeyConstraintString("", new String[0], "", new String[0], false));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> dialect.getAddPrimaryKeyConstraintString(""));
    }
}
