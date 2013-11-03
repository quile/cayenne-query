package quile.CayenneQuery;

import org.apache.cayenne.BaseContext;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SortOrder;
import org.apache.cayenne.exp.Expression;

import java.util.List;
import java.util.ArrayList;

class Query {

    private ObjectContext _objectContext;
    private int _limit;
    private int _offset;
    private boolean _distinct;
    private List<Ordering> _sortOrderings = new ArrayList<Ordering>();
    private List<Expression> _expressions = new ArrayList<Expression>();
    private List<Object> _parameters = new ArrayList<Object>();

    public ObjectContext _objectContext() {
        if (_objectContext == null) {
            _objectContext = BaseContext.getThreadObjectContext();
        }
        return _objectContext;
    }

    public Query() {
        // initialise empty query object with sensible defaults
    }

    public Query(ObjectContext objectContext) {
        this._objectContext = objectContext;
    }

    public Query(DataContext dataContext) {
        this._objectContext = dataContext;
    }

    public Query limit(int limit) {
        _limit = limit;
        return this;
    }

    public Query offset(int offset) {
        _offset = offset;
        return this;
    }

    public Query orderBy(String path, boolean isAscending, boolean ignoreCase) {
        SortOrder sortOrder = SortOrder.ASCENDING;

        if (isAscending) {
            if (ignoreCase) {
                sortOrder = SortOrder.ASCENDING_INSENSITIVE;
            }
        } else {
            if (ignoreCase) {
                sortOrder = SortOrder.DESCENDING_INSENSITIVE;
            } else {
                sortOrder = SortOrder.DESCENDING;
            }
        }
        Ordering o = new Ordering(path, sortOrder);
        return this;
    }

    public Query orderBy(String path, boolean isAscending) {
        return orderBy(path, isAscending, false);
    }

    public Query orderBy(String path) {
        return orderBy(path, true, false);
    }

    public Query filter(String filter, Object... args) {
        Expression exp = Expression.fromString(filter);
        _expressions.add(exp);

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                _parameters.add(args[i]);
            }
        }
        return this;
    }

    public static void main(String args[]) {
        System.out.println("foo!");
    }
}
