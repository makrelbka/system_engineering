<template>
  <div class="min-h-screen bg-white">
    <Header />

    <div v-if="!orderSubmitted" class="container mx-auto px-4 py-16 max-w-2xl">
      <div class="mb-8">
        <h1 class="text-4xl mb-2">Оформление заказа</h1>
        <p class="text-gray-600">Заполните контактные данные для завершения заказа</p>
      </div>

      <div class="mb-8 p-6 border-2 border-black rounded-lg bg-gray-50">
        <h2 class="text-xl mb-4">Ваш заказ</h2>
        <div class="space-y-3 mb-4">
          <div
            v-for="item in cart"
            :key="item.id"
            class="flex justify-between items-center gap-4"
          >
            <div class="flex-1">
              <span class="font-medium">{{ item.name }}</span>
            </div>
            <div class="flex items-center gap-2 border-2 border-black rounded">
              <button
                type="button"
                @click="decreaseQuantity(item.id)"
                class="px-3 py-1 hover:bg-gray-200 transition-colors font-bold"
                :disabled="item.quantity <= 1"
                :class="{ 'opacity-50 cursor-not-allowed': item.quantity <= 1 }"
              >
                −
              </button>
              <span class="px-3 py-1 min-w-[3rem] text-center font-medium">
                {{ item.quantity }}
              </span>
              <button
                type="button"
                @click="increaseQuantity(item.id)"
                class="px-3 py-1 hover:bg-gray-200 transition-colors font-bold"
              >
                +
              </button>
            </div>
            <span class="font-medium min-w-[6rem] text-right">
              ${{ (item.price * item.quantity).toFixed(2) }}
            </span>
            <button
              type="button"
              @click="removeItem(item.id)"
              class="ml-2 text-red-600 hover:text-red-800 font-bold"
              title="Удалить"
            >
              ×
            </button>
          </div>
        </div>
        <div class="pt-4 border-t-2 border-black flex justify-between text-lg font-semibold">
          <span>Итого:</span>
          <span>${{ totalPrice.toFixed(2) }}</span>
        </div>
      </div>

      <form @submit.prevent="handleSubmit" class="space-y-6 bg-white p-8 border-2 border-black rounded-lg">
        <div>
          <label for="name" class="block text-sm font-medium mb-2">
            Ваше имя *
          </label>
          <Input
            id="name"
            type="text"
            v-model="formData.name"
            placeholder="Введите ваше имя"
            class="border-black"
            required
          />
        </div>

        <div>
          <label for="phone" class="block text-sm font-medium mb-2">
            Телефон *
          </label>
          <Input
            id="phone"
            type="tel"
            v-model="formData.phone"
            placeholder="+7 (___) ___-__-__"
            class="border-black"
            required
          />
        </div>

        <div>
          <label for="email" class="block text-sm font-medium mb-2">
            Email *
          </label>
          <Input
            id="email"
            type="email"
            v-model="formData.email"
            placeholder="your@email.com"
            class="border-black"
            required
          />
        </div>

        <div>
          <label for="address" class="block text-sm font-medium mb-2">
            Адрес доставки *
          </label>
          <Textarea
            id="address"
            v-model="formData.address"
            placeholder="Укажите полный адрес доставки"
            class="border-black min-h-[100px]"
            required
          />
        </div>

        <div>
          <label for="comment" class="block text-sm font-medium mb-2">
            Комментарий к заказу (необязательно)
          </label>
          <Textarea
            id="comment"
            v-model="formData.comment"
            placeholder="Дополнительные пожелания..."
            class="border-black min-h-[80px]"
          />
        </div>

        <div class="flex gap-4">
          <Button
            type="button"
            variant="outline"
            class="flex-1 border-black"
            @click="$router.push('/')"
          >
            Вернуться к покупкам
          </Button>
          <Button
            type="submit"
            class="flex-1 bg-black hover:bg-gray-800"
            :disabled="cart.length === 0"
          >
            Оформить заказ
          </Button>
        </div>
      </form>
    </div>

    <div v-else class="container mx-auto px-4 py-16 max-w-2xl">
      <div class="text-center p-12 border-2 border-black rounded-lg bg-gray-50">
        <div class="mb-6">
          <svg
            class="mx-auto h-16 w-16 text-green-600"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
        </div>
        <h2 class="text-3xl mb-4 font-semibold">Заказ оформлен!</h2>
        <p class="text-lg text-gray-700 mb-2">
          Ждите звонка с номера
        </p>
        <p class="text-2xl font-bold mb-6">
          +7 (495) 123-45-67
        </p>
        <p class="text-gray-600 mb-8">
          для подтверждения заказа
        </p>
        <Button
          class="bg-black hover:bg-gray-800"
          @click="handleBackToHome"
        >
          Вернуться на главную
        </Button>
      </div>
    </div>

    <Footer />
    <Toaster />
  </div>
</template>

<script setup>
import { ref, computed, inject, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useToast } from '../composables/useToast'
import Header from '../components/Header.vue'
import Footer from '../components/Footer.vue'
import Toaster from '../components/Toaster.vue'
import Button from '../components/ui/Button.vue'
import Input from '../components/ui/Input.vue'
import Textarea from '../components/ui/Textarea.vue'

const router = useRouter()
const { toast } = useToast()

const cart = inject('cart')

const orderSubmitted = ref(false)

onMounted(() => {
  if (cart.value.length === 0) {
    toast.error('Корзина пуста')
    router.push('/')
  }
})

const formData = ref({
  name: '',
  phone: '',
  email: '',
  address: '',
  comment: ''
})

const totalPrice = computed(() => {
  return cart.value.reduce((sum, item) => sum + item.price * item.quantity, 0)
})

const increaseQuantity = (itemId) => {
  const item = cart.value.find(i => i.id === itemId)
  if (item) {
    item.quantity++
  }
}

const decreaseQuantity = (itemId) => {
  const item = cart.value.find(i => i.id === itemId)
  if (item && item.quantity > 1) {
    item.quantity--
  }
}

const removeItem = (itemId) => {
  const index = cart.value.findIndex(i => i.id === itemId)
  if (index !== -1) {
    cart.value.splice(index, 1)
    if (cart.value.length === 0) {
      toast.info('Корзина пуста')
      router.push('/')
    }
  }
}

const handleSubmit = async () => {
  if (cart.value.length === 0) {
    toast.error('Корзина пуста')
    return
  }

  if (!formData.value.name || !formData.value.phone || !formData.value.email || !formData.value.address) {
    toast.error('Пожалуйста, заполните все обязательные поля')
    return
  }

  try {

    const apiUrl = import.meta.env.VITE_API_URL || '/api'
    const response = await fetch(`${apiUrl}/orders`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        customerName: formData.value.name,
        customerEmail: formData.value.email,
        customerPhone: formData.value.phone,
        deliveryAddress: formData.value.address,
        items: cart.value.map(item => ({
          productId: item.id,
          quantity: item.quantity
        }))
      })
    })

    if (!response.ok) {
      const error = await response.text()
      throw new Error(error || 'Ошибка при оформлении заказа')
    }

    orderSubmitted.value = true
    toast.success('Заказ успешно оформлен!')

    cart.value = []
  } catch (error) {
    toast.error(error.message || 'Не удалось оформить заказ')
  }
}

const handleBackToHome = () => {

  orderSubmitted.value = false
  formData.value = {
    name: '',
    phone: '',
    email: '',
    address: '',
    comment: ''
  }
  router.push('/')
}
</script>
