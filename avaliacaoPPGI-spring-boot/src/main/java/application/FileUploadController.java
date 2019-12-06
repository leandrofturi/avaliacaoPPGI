package application;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import application.storage.FileSystemStorageService;
import application.storage.StorageFileNotFoundException;
import avaliacaoPPGI.AvaliacaoPPGI;
import exceptions.CodNaoDefinido;
import exceptions.CodigoRepetido;
import exceptions.Desconhecido;
import exceptions.ErroDeFormatacao;
import exceptions.ErroDeIO;
import exceptions.QualiDesconhecidoRegra;
import exceptions.QualiDesconhecidoVeiculo;
import exceptions.SiglaVeiculoNaoDefinida;
import exceptions.VeiculoDesconhecido;

@Controller
public class FileUploadController {

	private final FileSystemStorageService storageService;
	
	private int index = 0;
	private String[] fileNames = new String[] {"docentes.csv", "veiculos.csv", "publicacoes.csv", "qualis.csv", "regras.csv", "ano.csv"};
	private String[] filesMessage = new String[] {"Docentes", "Veículos", "Publicações", "Qualificações", "Regras de recredenciamento", "Ano de Recredenciamento"};

	@Autowired
	public FileUploadController(FileSystemStorageService storageService) {
		this.storageService = storageService;
		index = 0;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", (path.getFileName().toString())).build().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/")
	public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		storageService.store(file, "in/" + fileNames[index]);
		redirectAttributes.addFlashAttribute("message",
				"Você carregou o arquido de " + filesMessage[index] + "!" + '\n' +
				"Agora carregue " + filesMessage[(index+1) % filesMessage.length]);

		index++;
		if(index >= fileNames.length) {
			
			redirectAttributes.addFlashAttribute("title", "Você carregou todos os arquivos necessários!");
			redirectAttributes.addFlashAttribute("message", "Construindo relatórios...");
			try {
				AvaliacaoPPGI.run();
			} catch (ErroDeIO | ErroDeFormatacao | CodigoRepetido | VeiculoDesconhecido | SiglaVeiculoNaoDefinida
					| QualiDesconhecidoVeiculo | Desconhecido | CodNaoDefinido | QualiDesconhecidoRegra e) {
				redirectAttributes.addFlashAttribute("title", "Você não carregou todos os arquivos necessários!");
				redirectAttributes.addFlashAttribute("message", "Não terá relatórios por agora.");
				redirectAttributes.addFlashAttribute("erro", e.getMessage());
			}
			
			index = 0;
			this.storageService.deleteIn();
		}
		return "redirect:/";
	}
	
	@GetMapping("/home")
	public String home(RedirectAttributes redirectAttributes) {
		
		redirectAttributes.addFlashAttribute("title", "Aqui você pode carregar arquivos específicos e gerar arquivos mais específicos ainda");
		redirectAttributes.addFlashAttribute("message",
				"Inicie carregando o arquivo de Docentes");
		
		return "redirect:/";
	}
	
	@GetMapping("/new")
	public String handleFileUpload(RedirectAttributes redirectAttributes) {

		storageService.deleteAll();
		storageService.init();
		index = 0;
		redirectAttributes.addFlashAttribute("message",
				"Inicie carregando o arquivo de Docentes");
		
		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
