package quile.CayenneQuery;

import org.apache.cayenne.BaseContext;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SortOrder;
import org.apache.cayenne.exp.Expression;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Query {

    private ObjectContext _objectContext;
    private int _limit;
    private int _offset;
    private boolean _distinct;
    private List<Ordering> _sortOrderings = new ArrayList<Ordering>();
    private List<Expression> _expressions = new ArrayList<Expression>();
    private Map<String, Object> _parameters = new HashMap<String, Object>();

    private Class _target;

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

    private const Pattern PARAMETER_REGEXP = Pattern.compile("\$([a-zA-Z0-9_-]+)");

    public Query filter(String filter, Object... args) {
        Expression exp = Expression.fromString(filter);
        _expressions.add(exp);

        Matcher m = PARAMETER_REGEXP.matcher(filter);

        List<String> parameters = new ArrayList<String>();
        while (m.find()) {
            parameters.add(m.group(1));
        }

        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                _parameters.put(parameters.get(i), args[i]);
            }
        }
        return this;
    }

    public SelectQuery selectQuery() {
        return selectQuery(_target);
    }

    public SelectQuery selectQuery(Class target) throws Exception {
        if (target == null) {
            throw new Exception("No target class specified for fetch");
        }
        SelectQuery s = new SelectQuery(target);

        Expression e = ExpressionFactory.joinExp(Expression.AND, _expressions);
        s.setQualifier(e);

        s.setFetchLimit(_limit);
        s.setFetchOffset(_offset);

        return s;
    }

    public static void main(String args[]) {
        System.out.println("foo!");
    }
}
