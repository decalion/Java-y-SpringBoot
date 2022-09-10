package com.icaballero.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import com.icaballero.app.models.Examen;
import com.icaballero.app.repositories.ExamenRepositoryImpl;
import com.icaballero.app.repositories.IExamenRepository;
import com.icaballero.app.repositories.IPreguntasRepository;
import com.icaballero.app.repositories.PreguntasRepositoryImpl;
import com.icaballero.app.services.ExamenServicesImpl;
import com.icaballero.app.services.IExamenService;

//Habilitar el uso de anotaciones
@ExtendWith(MockitoExtension.class)
public class ExamenServicesImplTest {
	
	
	@InjectMocks
	ExamenServicesImpl service;
	
	@Mock
	IExamenRepository examenRepo;
	
	@Mock
	IPreguntasRepository preguntasRepo;
	
	//Spy llama al metodo real
	@Spy
	PreguntasRepositoryImpl pRepoImpl;
	
	@Captor
	ArgumentCaptor<Long> captor;
	
	
//	@BeforeEach
//	void setUp() {
//		//Habilitar el uso de anotaciones
//		MockitoAnnotations.openMocks(this);
//		
//	}
//	
	
	@Test
	void findExamenByNameTest() {
		
		when(examenRepo.findAll()).thenReturn(getData());
		
		Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
		
		assertTrue(examen.isPresent());
		assertEquals(5L, examen.orElseThrow().getId());
		assertEquals("Matematicas", examen.get().getNombre());
		
		
		
	}
	
	@Test
	void findExamenByNameEmptyListTest() {
		
		List<Examen> datos = Collections.emptyList();
		
		when(examenRepo.findAll()).thenReturn(datos);
		
		Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
		
		assertFalse(examen.isPresent());
		
		
		
	}
	
	@Test
	void findExamenByNameWithPreguntasTest() {
		
		when(examenRepo.findAll()).thenReturn(getData());
		
		when(preguntasRepo.findPreguntasByExamenId(Mockito.anyLong())).thenReturn(getPreguntas());
		
		Examen examen = service.findExamenByNameWithPreguntas("Matematicas");
		
		assertEquals(5, examen.getPreguntas().size());
		
	}

	
	@Test
	void findExamenByNameWithPreguntasVerifyTest() {
		
		when(examenRepo.findAll()).thenReturn(getData());
		
		when(preguntasRepo.findPreguntasByExamenId(Mockito.anyLong())).thenReturn(getPreguntas());
		
		Examen examen = service.findExamenByNameWithPreguntas("Matematicas");
		
		assertEquals(5, examen.getPreguntas().size());
		
		//Comprobar que se ejecutan los metodos del mock
		verify(examenRepo).findAll();
		verify(preguntasRepo).findPreguntasByExamenId(anyLong());
		
	}
	
	@Test
	void saveExamenTest() {
		
		when(examenRepo.save(any(Examen.class))).then(new Answer<Examen>() {
			
			Long secuncia = 8L;

			@Override
			public Examen answer(InvocationOnMock invocation) throws Throwable {
				Examen examen = invocation.getArgument(0);
				examen.setId(secuncia++);
				
				return examen;
			}
		});
		
		Examen examen = service.save(getExamen());
		
		assertNotNull(examen);
		assertEquals(8L, examen.getId());
		assertEquals("Matematicas",examen.getNombre());
		
		verify(examenRepo).save(any(Examen.class));
		
		verify(preguntasRepo).save(anyList());
		
	}
	
	@Test
	void throwTest() {
		when(examenRepo.findAll()).thenReturn(getData());
		
		when(preguntasRepo.findPreguntasByExamenId(Mockito.anyLong())).thenThrow(IllegalArgumentException.class);
		
		assertThrows(IllegalArgumentException.class, () -> {
			
			service.findExamenByNameWithPreguntas("Matematicas");
		});
		
		verify(examenRepo).findAll();
		verify(preguntasRepo).findPreguntasByExamenId(anyLong());
	}
	
	@Test
	void argumentsMatchersTest() {
		
		when(examenRepo.findAll()).thenReturn(getData());
		
		when(preguntasRepo.findPreguntasByExamenId(Mockito.anyLong())).thenReturn(getPreguntas());
		
		service.findExamenByNameWithPreguntas("Matematicas");
		
		verify(examenRepo).findAll();
		verify(preguntasRepo).findPreguntasByExamenId(Mockito.argThat(arg -> arg.equals(5L)));
		verify(preguntasRepo).findPreguntasByExamenId(Mockito.eq(5L));
		
		
	}
	
	@Test
	void argumentsCaptorTest() {
		
		when(examenRepo.findAll()).thenReturn(getData());
		
		when(preguntasRepo.findPreguntasByExamenId(Mockito.anyLong())).thenReturn(getPreguntas());
		
		service.findExamenByNameWithPreguntas("Matematicas");
		
		//ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
		
		verify(preguntasRepo).findPreguntasByExamenId(captor.capture());
		
		assertEquals(5L, captor.getValue());
		
	}
	
	@Test
	void doThrowTest() {
		
		doThrow(IllegalArgumentException.class).when(preguntasRepo).save(anyList());
		
		assertThrows(IllegalArgumentException.class, () -> {
			
			service.save(getExamen());
		});
	
		
		
	}
	
	@Test
	void doAnswerTest() {
		when(examenRepo.findAll()).thenReturn(getData());
		
		
		doAnswer(invocation -> {
			Long id = invocation.getArgument(0);
			
			return id == 5L ? getPreguntas() : null;
		}).when(preguntasRepo).findPreguntasByExamenId(anyLong());
		
		Examen examen = service.findExamenByNameWithPreguntas("Matematicas");
		
		assertEquals(5L, examen.getId());
		assertEquals("Matematicas", examen.getNombre());
		
		
		verify(preguntasRepo).findPreguntasByExamenId(anyLong());
	}
	
	
	@Test
	void doCallRealMethodTest() {
		
		when(examenRepo.findAll()).thenReturn(getData());
		
		//No hace falta ya que si no se mockea se llama al metodo real
		//doCallRealMethod().when(pRepoImpl).findPreguntasByExamenId(anyLong());
		
		Examen examen = service.findExamenByNameWithPreguntas("Matematicas");
		
		assertEquals(5L, examen.getId());
		assertEquals("Matematicas", examen.getNombre());
		
	}
	
	@Test
	void spyTest() {
		
		//Los spy ejecutan metodos reales
		
		IExamenRepository examenRepository = spy(ExamenRepositoryImpl.class);
		IPreguntasRepository preguntasRepository = spy(PreguntasRepositoryImpl.class);
		
		IExamenService  examenService = new ExamenServicesImpl();
		
		
		
	}
	
	@Test
	void ordenInvocacionesTest() {
		
		when(examenRepo.findAll()).thenReturn(getData());
		
		when(preguntasRepo.findPreguntasByExamenId(Mockito.anyLong())).thenReturn(getPreguntas());
		
		Examen examen = service.findExamenByNameWithPreguntas("Matematicas");
		Examen examen2 = service.findExamenByNameWithPreguntas("Lenguaje");
		
		//Verificar en que orden se llaman los mocks (Se tienen que orden por orden de llamada)
		InOrder inOrder = inOrder(examenRepo,preguntasRepo);
		inOrder.verify(examenRepo).findAll();
		inOrder.verify(preguntasRepo).findPreguntasByExamenId(5L);
		inOrder.verify(examenRepo).findAll();
		inOrder.verify(preguntasRepo).findPreguntasByExamenId(6L);
		
	}
	
	
	@Test
	void numeroDeInvocacionesTest() {
		when(examenRepo.findAll()).thenReturn(getData());
		
		Examen examen = service.findExamenByNameWithPreguntas("Matematicas");
		
		verify(preguntasRepo).findPreguntasByExamenId(5L);
		//Comprobar las veces que se ejecuta
		verify(preguntasRepo, times(1)).findPreguntasByExamenId(5L);
		//Comprobar que se ejecuta 1 vez
		verify(preguntasRepo, atLeast(1)).findPreguntasByExamenId(5L);
		//Comprobar que se ejecuta 1 vez
		verify(preguntasRepo, atLeastOnce()).findPreguntasByExamenId(5L);
		//Comprobar que como maximo se ejecute 1 vez
		verify(preguntasRepo, atMost(1)).findPreguntasByExamenId(5L);
		//Comprobar que como maximo se ejecute 1 vez
		verify(preguntasRepo, atMostOnce()).findPreguntasByExamenId(5L);
	}
	
	
	private Examen getExamen() {
		Examen e = new Examen(8L,"Matematicas");
		e.setPreguntas(getPreguntas());
		return e;
	}

	private List<String> getPreguntas() {
		return Arrays.asList("Pregunta1","Pregunta2","Pregunta3","Pregunta4","Pregunta5");
	}

	private List<Examen> getData() {
		
		return Arrays.asList(new Examen(5L,"Matematicas"),new Examen(6L,"Lenguaje") ,
				new Examen(7L,"Historia"));
	}

}
