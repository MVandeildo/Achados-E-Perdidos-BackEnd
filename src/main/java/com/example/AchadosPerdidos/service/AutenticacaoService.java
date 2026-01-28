@Service
public class AutenticacaoService implements UserDetailsService{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loaadUserByUsername(String username) throws UsernameNotFoundException{
        return repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado"));
    }
}