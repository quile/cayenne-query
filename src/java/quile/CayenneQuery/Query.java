package quile.CayenneQuery;

import org.apache.cayenne.BaseContext;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.Ordering;
import java.util.List;
import java.util.ArrayList;

class Query {

    private ObjectContext _objectContext;
    private int _limit;
    private int _offset;
    private boolean _distinct;
    private List<Ordering> _sortOrderings = new ArrayList<Ordering>();

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

    public static void main(String args[]) {
        System.out.println("foo!");
    }
}
