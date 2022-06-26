import java.util.List;

class Hex {
    String hex_type;
    List<Land_object> lands;
    List<Unit_object> units;
    List<Shot_object> shots;
    List<Fallen_object> fallens;
    List<Barrier_object> barriers;

    Hex(List<Land_object> lands, List<Unit_object> units, List<Shot_object> shots,
        List<Fallen_object> fallens, List<Barrier_object> barriers) {
        this.lands = lands;
        this.units = units;
        this.shots = shots;
        this.fallens = fallens;
        this.barriers = barriers;
    }
}
