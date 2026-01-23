<template>
  <div class="min-h-screen bg-white">

    <div v-if="!isAuthenticated" class="min-h-screen flex items-center justify-center bg-gray-50">
      <div class="w-full max-w-md p-8 bg-white border-2 border-black rounded-lg">
        <h1 class="text-3xl mb-6 text-center">Админ-панель</h1>
        <form @submit.prevent="handleLogin" class="space-y-4">
          <div>
            <label class="block text-sm font-medium mb-2">Email</label>
            <Input
              v-model="loginForm.email"
              type="email"
              placeholder="admin@flowershop.ru"
              class="border-black"
              required
            />
          </div>
          <div>
            <label class="block text-sm font-medium mb-2">Пароль</label>
            <Input
              v-model="loginForm.password"
              type="password"
              placeholder="Введите пароль"
              class="border-black"
              required
            />
          </div>
          <Button type="submit" class="w-full bg-black hover:bg-gray-800">
            Войти
          </Button>
        </form>
      </div>
    </div>

    <div v-else class="container mx-auto px-4 py-8">
      <div class="mb-6 flex justify-between items-center">
        <h1 class="text-4xl">Админ-панель</h1>
        <Button @click="handleLogout" variant="outline" class="border-black">
          Выйти
        </Button>
      </div>

      <div class="mb-6 border-b-2 border-black">
        <div class="flex gap-4">
          <button
            v-for="tab in tabs"
            :key="tab.id"
            @click="activeTab = tab.id"
            :class="[
              'px-4 py-2 font-medium border-b-2 transition-colors',
              activeTab === tab.id
                ? 'border-black text-black'
                : 'border-transparent text-gray-500 hover:text-black'
            ]"
          >
            {{ tab.name }}
          </button>
        </div>
      </div>

      <div v-if="activeTab === 'pending'" class="space-y-8">

        <div>
          <h2 class="text-2xl mb-4">Заказы (требуют обработки)</h2>
          <div v-if="pendingOrders.length === 0" class="text-gray-500 text-center py-8 border-2 border-dashed border-gray-300 rounded-lg">
            Нет заказов, требующих обработки
          </div>
          <div v-else class="space-y-4">
            <div
              v-for="order in pendingOrders"
              :key="order.id"
              class="p-6 border-2 border-black rounded-lg"
            >
              <div class="flex justify-between items-start mb-4">
                <div>
                  <h3 class="text-xl font-semibold">Заказ #{{ order.id }}</h3>
                  <p class="text-gray-600">{{ order.customerName }} ({{ order.customerEmail }})</p>
                  <p class="text-gray-600">{{ order.customerPhone }}</p>
                  <p class="text-gray-600">{{ order.deliveryAddress }}</p>
                </div>
                <div class="text-right">
                  <p class="text-2xl font-bold">${{ order.totalPrice.toFixed(2) }}</p>
                  <span class="px-3 py-1 bg-yellow-100 rounded">{{ order.status }}</span>
                </div>
              </div>
              <div class="mb-4">
                <h4 class="font-medium mb-2">Товары:</h4>
                <ul class="space-y-1">
                  <li v-for="item in order.items" :key="item.id">
                    {{ item.productName }} × {{ item.quantity }} — ${{ (item.price * item.quantity).toFixed(2) }}
                  </li>
                </ul>
              </div>
              <div class="flex gap-2">
                <Button
                  @click="updateOrderStatus(order.id, 'IN_PROCESS')"
                  variant="outline"
                  class="border-black"
                >
                  В обработке
                </Button>
                <Button
                  @click="updateOrderStatus(order.id, 'COMPLETED')"
                  class="bg-green-600 hover:bg-green-700"
                >
                  Выполнен
                </Button>
              </div>
            </div>
          </div>
        </div>

        <div>
          <h2 class="text-2xl mb-4">Заявки на звонок (требуют обработки)</h2>
          <div v-if="pendingCallbacks.length === 0" class="text-gray-500 text-center py-8 border-2 border-dashed border-gray-300 rounded-lg">
            Нет заявок, требующих обработки
          </div>
          <div v-else class="space-y-4">
            <div
              v-for="callback in pendingCallbacks"
              :key="callback.id"
              class="p-6 border-2 border-black rounded-lg"
            >
              <div class="flex justify-between items-start">
                <div>
                  <h3 class="text-xl font-semibold">{{ callback.name }}</h3>
                  <p class="text-gray-600">{{ callback.phone }}</p>
                  <p class="text-sm text-gray-500">{{ new Date(callback.createdAt).toLocaleString('ru-RU') }}</p>
                </div>
                <Button
                  @click="markCallbackCompleted(callback.id)"
                  class="bg-green-600 hover:bg-green-700"
                >
                  Выполнено
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'products'" class="space-y-6">
        <div class="p-6 border-2 border-black rounded-lg">
          <h2 class="text-2xl mb-4">Добавить товар</h2>
          <form @submit.prevent="createProduct" class="grid gap-4 md:grid-cols-2">
            <div class="md:col-span-1">
              <label class="block text-sm font-medium mb-2">Название</label>
              <Input v-model="productForm.name" type="text" class="border-black" required />
            </div>
            <div class="md:col-span-1">
              <label class="block text-sm font-medium mb-2">Цена</label>
              <Input v-model="productForm.price" type="number" step="0.01" min="0" class="border-black" required />
            </div>
            <div class="md:col-span-2">
              <label class="block text-sm font-medium mb-2">Описание</label>
              <Textarea v-model="productForm.description" class="border-black min-h-[80px]" />
            </div>
            <div class="md:col-span-2">
              <label class="block text-sm font-medium mb-2">Фото (URL)</label>
              <Input v-model="productForm.image" type="text" class="border-black" />
            </div>
            <div class="md:col-span-2">
              <label class="block text-sm font-medium mb-2">Фото (PNG файл)</label>
              <input
                type="file"
                accept="image/png"
                class="block w-full text-sm text-gray-700 file:mr-4 file:py-2 file:px-4 file:border-2 file:border-black file:bg-white file:text-sm file:font-semibold file:cursor-pointer"
                @change="handleImageUpload"
              />
              <p v-if="productForm.imageFileName" class="text-xs text-gray-500 mt-1">
                Выбран файл: {{ productForm.imageFileName }}
              </p>
            </div>
            <div class="md:col-span-2">
              <Button type="submit" class="bg-black hover:bg-gray-800">
                Добавить товар
              </Button>
            </div>
          </form>
        </div>

        <div>
          <h2 class="text-2xl mb-4">Список товаров</h2>
          <div v-if="products.length === 0" class="text-gray-500 text-center py-8 border-2 border-dashed border-gray-300 rounded-lg">
            Товаров нет
          </div>
          <div v-else class="grid gap-4 md:grid-cols-2">
            <div
              v-for="product in products"
              :key="product.id"
              class="p-4 border-2 border-black rounded-lg flex gap-4"
            >
              <img
                v-if="product.image"
                :src="product.image"
                :alt="product.name"
                class="h-20 w-20 rounded-md object-cover border-2 border-black"
              />
              <div class="flex-1">
                <h3 class="text-lg font-semibold">{{ product.name }}</h3>
                <p class="text-sm text-gray-600">${{ Number(product.price).toFixed(2) }}</p>
                <p v-if="product.description" class="text-sm text-gray-500 mt-1">
                  {{ product.description }}
                </p>
              </div>
              <Button
                variant="ghost"
                size="sm"
                class="text-red-600"
                @click="deleteProduct(product.id)"
              >
                Удалить
              </Button>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'callbacks-completed'" class="space-y-4">
        <h2 class="text-2xl mb-4">Выполненные заявки на звонок</h2>
        <div v-if="completedCallbacks.length === 0" class="text-gray-500 text-center py-8">
          Нет выполненных заявок
        </div>
        <div v-else class="space-y-4">
          <div
            v-for="callback in completedCallbacks"
            :key="callback.id"
            class="p-6 border-2 border-gray-300 rounded-lg bg-gray-50"
          >
            <div>
              <h3 class="text-xl font-semibold">{{ callback.name }}</h3>
              <p class="text-gray-600">{{ callback.phone }}</p>
              <p class="text-sm text-gray-500">{{ new Date(callback.createdAt).toLocaleString('ru-RU') }}</p>
            </div>
          </div>
        </div>
      </div>

      <div v-if="activeTab === 'orders-completed'" class="space-y-4">
        <h2 class="text-2xl mb-4">Выполненные заказы</h2>
        <div v-if="completedOrders.length === 0" class="text-gray-500 text-center py-8">
          Нет выполненных заказов
        </div>
        <div v-else class="space-y-4">
          <div
            v-for="order in completedOrders"
            :key="order.id"
            class="p-6 border-2 border-gray-300 rounded-lg bg-gray-50"
          >
            <div class="flex justify-between items-start">
              <div>
                <h3 class="text-xl font-semibold">Заказ #{{ order.id }}</h3>
                <p class="text-gray-600">{{ order.customerName }} ({{ order.customerEmail }})</p>
                <p class="text-sm text-gray-500">{{ new Date(order.createdAt).toLocaleString('ru-RU') }}</p>
              </div>
              <div class="text-right">
                <p class="text-2xl font-bold">${{ order.totalPrice.toFixed(2) }}</p>
                <span class="px-3 py-1 bg-green-100 rounded">Выполнен</span>
              </div>
            </div>
          </div>
        </div>
      </div>

    </div>

    <Toaster />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useToast } from '../composables/useToast'
import Button from '../components/ui/Button.vue'
import Input from '../components/ui/Input.vue'
import Textarea from '../components/ui/Textarea.vue'
import Toaster from '../components/Toaster.vue'

const { toast } = useToast()
const apiUrl = import.meta.env.VITE_API_URL || '/api'

const isAuthenticated = ref(false)
const currentUser = ref(null)
const activeTab = ref('pending')

const tabs = [
  { id: 'pending', name: 'Ожидающие' },
  { id: 'products', name: 'Товары' },
  { id: 'callbacks-completed', name: 'Выполненные звонки' },
  { id: 'orders-completed', name: 'Выполненные заказы' }
]

const loginForm = ref({
  email: '',
  password: ''
})

const pendingOrders = ref([])
const completedOrders = ref([])
const pendingCallbacks = ref([])
const completedCallbacks = ref([])
const products = ref([])

const productForm = ref({
  name: '',
  price: '',
  description: '',
  image: '',
  imageFileName: ''
})

const handleLogin = async () => {
  try {
    const response = await fetch(`${apiUrl}/admin/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(loginForm.value)
    })

    const data = await response.json()
    if (data.success) {
      isAuthenticated.value = true
      currentUser.value = data

      localStorage.setItem('admin_auth', JSON.stringify(data))
      await loadData()
    } else {
      toast.error(data.message || 'Неверный email или пароль')
    }
  } catch (error) {
    toast.error('Ошибка при входе')
  }
}

const handleLogout = () => {
  isAuthenticated.value = false
  currentUser.value = null
  loginForm.value = { email: '', password: '' }

  localStorage.removeItem('admin_auth')
}

const loadData = async () => {
  try {
    const responses = await Promise.all([
      fetch(`${apiUrl}/admin/orders/pending`).then(r => {
        if (!r.ok) throw new Error('Failed to load pending orders')
        return r.json()
      }),
      fetch(`${apiUrl}/admin/orders/completed`).then(r => {
        if (!r.ok) throw new Error('Failed to load completed orders')
        return r.json()
      }),
      fetch(`${apiUrl}/admin/callbacks/pending`).then(r => {
        if (!r.ok) throw new Error('Failed to load pending callbacks')
        return r.json()
      }),
      fetch(`${apiUrl}/admin/callbacks/completed`).then(r => {
        if (!r.ok) throw new Error('Failed to load completed callbacks')
        return r.json()
      }),
      fetch(`${apiUrl}/admin/products`).then(r => {
        if (!r.ok) throw new Error('Failed to load products')
        return r.json()
      })
    ])

    pendingOrders.value = responses[0] || []
    completedOrders.value = responses[1] || []
    pendingCallbacks.value = responses[2] || []
    completedCallbacks.value = responses[3] || []
    products.value = responses[4] || []
  } catch (error) {
    console.error('Ошибка при загрузке данных:', error)
    toast.error('Ошибка при загрузке данных')

  }
}

const updateOrderStatus = async (orderId, status) => {
  try {
    await fetch(`${apiUrl}/admin/orders/${orderId}/status`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ status })
    })
    toast.success('Статус заказа обновлен')
    await loadData()
  } catch (error) {
    toast.error('Ошибка при обновлении статуса')
  }
}

const markCallbackCompleted = async (id) => {
  try {
    await fetch(`${apiUrl}/admin/callbacks/${id}/complete`, {
      method: 'PUT'
    })
    toast.success('Заявка отмечена как выполненная')
    await loadData()
  } catch (error) {
    toast.error('Ошибка при обновлении заявки')
  }
}

const createProduct = async () => {
  try {
    const payload = {
      name: productForm.value.name,
      price: productForm.value.price,
      description: productForm.value.description,
      image: productForm.value.image
    }

    const response = await fetch(`${apiUrl}/admin/products`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    })

    if (!response.ok) {
      const message = await response.text()
      throw new Error(message || 'Ошибка при добавлении товара')
    }

    toast.success('Товар добавлен')
    productForm.value = { name: '', price: '', description: '', image: '', imageFileName: '' }
    await loadData()
  } catch (error) {
    toast.error(error.message || 'Ошибка при добавлении товара')
  }
}

const deleteProduct = async (id) => {
  try {
    const response = await fetch(`${apiUrl}/admin/products/${id}`, {
      method: 'DELETE'
    })
    if (!response.ok) {
      const message = await response.text()
      throw new Error(message || 'Ошибка при удалении товара')
    }
    toast.success('Товар удален')
    await loadData()
  } catch (error) {
    toast.error(error.message || 'Ошибка при удалении товара')
  }
}

const handleImageUpload = (event) => {
  const file = event.target.files && event.target.files[0]
  if (!file) return
  if (file.type !== 'image/png') {
    toast.error('Можно загрузить только PNG')
    event.target.value = ''
    return
  }

  const reader = new FileReader()
  reader.onload = () => {
    productForm.value.image = reader.result
    productForm.value.imageFileName = file.name
  }
  reader.onerror = () => {
    toast.error('Не удалось прочитать файл')
  }
  reader.readAsDataURL(file)
}

onMounted(() => {

  const savedAuth = localStorage.getItem('admin_auth')
  if (savedAuth) {
    try {
      const authData = JSON.parse(savedAuth)
      isAuthenticated.value = true
      currentUser.value = authData

      loadData().catch(err => {
        console.error('Ошибка при загрузке данных:', err)

      })
    } catch (error) {
      console.error('Ошибка при восстановлении сессии:', error)
      localStorage.removeItem('admin_auth')
    }
  }

  watch(activeTab, () => {
    if (isAuthenticated.value) {
      loadData().catch(err => {
        console.error('Ошибка загрузки при смене таба:', err)
      })
    }
  })
})
</script>
